
package com.mygdx.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.custom.FunctionHelper;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJAnimatingActor;
import com.mygdx.custom.GJEnemy;
import com.mygdx.custom.GJScreen;
import com.mygdx.custom.GJUnit;
import com.mygdx.custom.GJUnitGrid;
import com.mygdx.custom.HealthBar;
import com.mygdx.custom.TargetGrid;
import com.mygdx.data.EnemyData;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.AnimationListener;
import com.mygdx.util.SHText;


public class BattleScreen extends GJScreen {

    private boolean hasAnimationPlaying = false;

    private TargetGrid unitGrid;

    private TargetGrid enemyGrid;

    private int unitCounter = 0;

    private int currentStage = 0;

    private GJUnit currentUnit;

    private GJEnemy currentEnemy;

    private List<UnitData> units;

    private List<EnemyData> enemies;

    private float time = 30.0f;

    private int whosAttacking;

    private static final int ENEMY_TURN = 0;

    private static final int PLAYER_TURN = 1;

    private HealthBar healthUnits;

    private HealthBar healthEnemies;

    private SHText battleResult;

    private SHText timer;

    private SHText next;
    
    private SHText restart;

    private List<StageData> stages;

    public BattleScreen(MyGdxGame game, TargetGrid unitGrid, TargetGrid enemyGrid, GJActor background, List<StageData> stages) {
        super(game);
        Gdx.app.log("lem", "startedBattle");
        this.stages = stages;
        this.unitGrid = unitGrid;
        this.enemyGrid = enemyGrid;

        units = new ArrayList<UnitData>();

        battleResult = new SHText("", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2, SHText.Size.XXLARGE, Color.BLACK);
        
        next = new SHText("Next Battle!", 0, 0, SHText.Size.XXLARGE, Color.BLACK);
        restart = new SHText("Restart Battle!", 0,0, SHText.Size.XXLARGE, Color.BLACK);
        
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentStage++;
                setupStage();
            }
        });
        
        restart.addListener(new ClickListener(){
           @Override
        public void clicked(InputEvent event, float x, float y) {
               setupStage();
           } 
        });

        next.setPosition(MyGdxGame.WIDTH / 2 - next.getWidth() / 2, battleResult.getY() - next.getHeight());
        restart.setPosition(MyGdxGame.WIDTH/2- restart.getWidth()/2,  next.getY() - restart.getHeight());

        healthEnemies = new HealthBar();
        healthUnits = new HealthBar();

        setupStage();

        healthUnits.setPosition(MyGdxGame.WIDTH - healthUnits.getWidth() - 5.0f, MyGdxGame.HEIGHT - healthUnits.getHeight() - 20.0f);

        healthEnemies.setPosition(enemyGrid.getX() + 5.0f, MyGdxGame.HEIGHT - healthEnemies.getHeight() - 20.0f);

        next.setVisible(false);
        restart.setVisible(false);
        timer = new SHText("30", 0, 0, SHText.Size.XXLARGE, Color.BLACK);

        timer.setPosition(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - timer.getHeight() - 20.0f);

        unitGrid.setUpForBattle();
        enemyGrid.setUpForBattle();

        for (Actor actor : unitGrid.getChildren()) {
            if (((GJUnitGrid) (actor)).getUnit() != null) {
                units.add((((GJUnitGrid) (actor)).getUnit().getUnitData()));
            }
        }

        healthUnits.setPerfectHealth(getCurrentTotalHPUnit());

        stage.addActor(background);
        stage.addActor(unitGrid);
        stage.addActor(enemyGrid);
        stage.addActor(healthUnits);
        stage.addActor(healthEnemies);
        stage.addActor(battleResult);
        stage.addActor(timer);
        stage.addActor(next);
        stage.addActor(restart);

        battleResult.setVisible(false);

        showEnemyGrid();
        whosAttacking = PLAYER_TURN;

        Gdx.input.setInputProcessor(stage);

    }

    private void showEnemyGrid() {
        for (Actor actor : enemyGrid.getChildren()) {
            actor.setVisible(true);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (time<=0){
            if (getCurrentTotalHPEnemy()>getCurrentTotalHPUnit()){
                battleResult.setText("BATTLE LOST!");
                battleResult.setVisible(true);
            }
            else{
                battleResult.setText("BATTLE WON!");
                battleResult.setX(MyGdxGame.WIDTH / 2 - battleResult.getWidth() / 2);
                battleResult.setY(MyGdxGame.HEIGHT / 2 + 100.0f);
                next.setVisible(true);
                restart.setVisible(true);
                battleResult.setVisible(true);
            }
        }
        else if (isAllEnemyDead()) {
            battleResult.setText("BATTLE WON!");
            battleResult.setX(MyGdxGame.WIDTH / 2 - battleResult.getWidth() / 2);
            battleResult.setY(MyGdxGame.HEIGHT / 2 + 100.0f);
            next.setVisible(true);
            restart.setVisible(true);
            battleResult.setVisible(true);
        } else if (isAllUnitsDead()) {
            battleResult.setText("BATTLE LOST!");
            battleResult.setVisible(true);
            restart.setVisible(true);
        } else {
            time -= delta;
            timer.setText(Integer.toString(Math.round(time)));
            if (whosAttacking == PLAYER_TURN) {

                if (currentUnit == null) {
                    currentUnit = (((GJUnitGrid) (unitGrid.getChildren().get(unitCounter))).getUnit());
                    unitCounter = currentUnit == null ? ++unitCounter : unitCounter;
                }

                if (currentUnit != null && !hasAnimationPlaying) {
                    if (currentUnit.getStatus() != GJAnimatingActor.ISDEAD) {
                        hasAnimationPlaying = true;
                        currentUnit.playAttackAnimation();

                        FunctionHelper.doDamage(((GJUnitGrid) (unitGrid.getChildren().get(unitCounter))), enemyGrid);
                        healthEnemies.updateHP(getCurrentTotalHPEnemy());
                        currentUnit.updateListener(new AnimationListener() {
                            @Override
                            public void onFinish() {
                                unitCounter++;
                                if (unitCounter < unitGrid.getChildren().size) {
                                    currentUnit = (((GJUnitGrid) (unitGrid.getChildren().get(unitCounter))).getUnit());
                                } else {
                                    unitCounter = 0;
                                    currentEnemy = null;
                                    whosAttacking = ENEMY_TURN;
                                }
                                hasAnimationPlaying = false;
                            }
                        });

                    } else {
                        unitCounter++;
                        if (unitCounter < unitGrid.getChildren().size) {
                            currentUnit = (((GJUnitGrid) (unitGrid.getChildren().get(unitCounter))).getUnit());
                        } else {
                            unitCounter = 0;
                            currentEnemy = null;
                            whosAttacking = ENEMY_TURN;
                        }
                        hasAnimationPlaying = false;
                    }
                }

                if (unitCounter > unitGrid.getChildren().size - 1) {
                    unitCounter = 0;
                    currentEnemy = null;
                    whosAttacking = ENEMY_TURN;
                }
            }

            else if (whosAttacking == ENEMY_TURN) {
                if (currentEnemy == null) {
                    currentEnemy = (((GJUnitGrid) (enemyGrid.getChildren().get(unitCounter))).getEnemy());
                    unitCounter = currentEnemy == null ? ++unitCounter : unitCounter;
                }

                if (currentEnemy != null && !hasAnimationPlaying) {
                    if (currentEnemy.getStatus() != GJAnimatingActor.ISDEAD) {
                        hasAnimationPlaying = true;
                        currentEnemy.playAttackAnimation();
                        FunctionHelper.doDamage(((GJUnitGrid) (enemyGrid.getChildren().get(unitCounter))), unitGrid);
                        healthUnits.updateHP(getCurrentTotalHPUnit());
                        currentEnemy.updateListener(new AnimationListener() {
                            @Override
                            public void onFinish() {
                                unitCounter++;
                                if (unitCounter < enemyGrid.getChildren().size) {
                                    currentEnemy = (((GJUnitGrid) (enemyGrid.getChildren().get(unitCounter))).getEnemy());
                                } else {
                                    unitCounter = 0;
                                    currentUnit = null;
                                    whosAttacking = PLAYER_TURN;
                                }
                                hasAnimationPlaying = false;
                            }
                        });
                    } else {
                        unitCounter++;
                        if (unitCounter < enemyGrid.getChildren().size) {
                            currentEnemy = (((GJUnitGrid) (enemyGrid.getChildren().get(unitCounter))).getEnemy());
                        } else {
                            unitCounter = 0;
                            currentUnit = null;
                            whosAttacking = PLAYER_TURN;
                        }
                        hasAnimationPlaying = false;
                    }
                }

                if (unitCounter > enemyGrid.getChildren().size - 1) {
                    unitCounter = 0;
                    currentUnit = null;
                    whosAttacking = PLAYER_TURN;
                }
            }
        }

    }

    private int getCurrentTotalHPEnemy() {
        int total_hp = 0;
        for (EnemyData enemyData : enemies) {

            total_hp += enemyData.getHp() < 0 ? 0 : enemyData.getHp();
        }
        return total_hp;
    }

    private int getCurrentTotalHPUnit() {
        int total_hp = 0;
        for (UnitData unitData : units) {
            total_hp += unitData.getHp() < 0 ? 0 : unitData.getHp();
        }
        return total_hp;
    }

    private boolean isAllEnemyDead() {
        return getCurrentTotalHPEnemy() <= 0;
    }

    private boolean isAllUnitsDead() {
        return getCurrentTotalHPUnit() <= 0;
    }

    private void setupStage() {
        enemyGrid.removeAllEnemies();

        StageData current_stage = stages.get(currentStage);

        enemies = null;
        enemies = new ArrayList<EnemyData>();

        for (EnemyData enemy : current_stage.getEnemies()) {
            enemies.add(enemy);
            TextureAtlas enemyAtlas = new TextureAtlas(Gdx.files.internal("enemies/" + enemy.getUnit_name() + "/preview.pack"));
            TextureRegion region = enemyAtlas.findRegion("preview");
            region.flip(true, false);
            enemyGrid.addToSpecificGrid(enemy.getCoordinates(), new GJEnemy(region, enemy));
        }

        healthEnemies.setPerfectHealth(getCurrentTotalHPEnemy());
        healthEnemies.updateHP(getCurrentTotalHPEnemy());
        battleResult.setVisible(false);
        next.setVisible(false);
        restart.setVisible(false);
        time = 30.0f;
        whosAttacking = PLAYER_TURN;
        hasAnimationPlaying = false;
    }

}
