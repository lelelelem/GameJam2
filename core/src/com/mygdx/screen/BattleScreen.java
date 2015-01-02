package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.custom.FunctionHelper;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJEnemy;
import com.mygdx.custom.GJScreen;
import com.mygdx.custom.GJUnit;
import com.mygdx.custom.GJUnitGrid;
import com.mygdx.custom.TargetGrid;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.AnimationListener;

public class BattleScreen extends GJScreen{
    
	private boolean hasAnimationPlaying = false;
    private TargetGrid unitGrid;
    private TargetGrid enemyGrid;
    private int unitCounter = 0;
    
    private GJUnit currentUnit;
    private GJEnemy currentEnemy;
    
    private int whosAttacking;
    
    private static final int ENEMY_TURN = 0;
    private static final int PLAYER_TURN = 1;
    
    public BattleScreen(MyGdxGame game,TargetGrid unitGrid, TargetGrid enemyGrid, GJActor background) {
        super(game);
        
        this.unitGrid = unitGrid;
        this.enemyGrid =enemyGrid;

        unitGrid.setUpForBattle();
        enemyGrid.setUpForBattle();
        
        stage.addActor(background);
        stage.addActor(unitGrid);
        stage.addActor(enemyGrid);
        
        showEnemyGrid();
        whosAttacking = PLAYER_TURN;
    }
    
    private void showEnemyGrid(){
        for(Actor actor:enemyGrid.getChildren()){
            actor.setVisible(true);
        }
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	
    	if (whosAttacking == PLAYER_TURN){

        	if (currentUnit == null){
        		Gdx.app.log("lem", "ctr "+unitCounter);
        		currentUnit = (((GJUnitGrid)(unitGrid.getChildren().get(unitCounter))).getUnit());
        		unitCounter = currentUnit == null ? ++unitCounter : unitCounter;
        		Gdx.app.log("lem", "init "+unitCounter);
        	}
        	
    		if (currentUnit != null && !hasAnimationPlaying) {
    			Gdx.app.log("lem", "inside");
    			hasAnimationPlaying = true;
    			currentUnit.playAttackAnimation();
    			FunctionHelper.doDamage(0, ((GJUnitGrid)(unitGrid.getChildren().get(unitCounter)))	, enemyGrid);
    			currentUnit.updateListener(new AnimationListener() {
    				@Override
    				public void onFinish() {
    					unitCounter++;
    					currentUnit = (((GJUnitGrid) (unitGrid.getChildren()
    							.get(unitCounter))).getUnit());
    					hasAnimationPlaying = false;
    				}
    			});
    		}
    		
    		if (unitCounter > unitGrid.getChildren().size-1){
    			unitCounter = 0;
    			whosAttacking = ENEMY_TURN;
    		}
    	}
    		
		else if (whosAttacking == ENEMY_TURN) {
			if (currentEnemy == null){
        		Gdx.app.log("lem", "ctr "+unitCounter);
        		currentEnemy = (((GJUnitGrid)(enemyGrid.getChildren().get(unitCounter))).getEnemy());
        		unitCounter = currentEnemy == null ? ++unitCounter : unitCounter;
        		Gdx.app.log("lem", "init "+unitCounter);
        	}
        	
    		if (currentEnemy != null && !hasAnimationPlaying) {
    			Gdx.app.log("lem", "inside");
    			hasAnimationPlaying = true;
    			currentEnemy.playAttackAnimation();
    			FunctionHelper.doDamage(0, ((GJUnitGrid)(enemyGrid.getChildren().get(unitCounter)))	, unitGrid);
    			currentEnemy.updateListener(new AnimationListener() {
    				@Override
    				public void onFinish() {
    					Gdx.app.log("lem", "finish");
    					unitCounter++;
    					currentEnemy = (((GJUnitGrid) (enemyGrid.getChildren()
    							.get(unitCounter))).getEnemy());
    					hasAnimationPlaying = false;
    				}
    			});
    		}
    		
    		if (unitCounter > enemyGrid.getChildren().size-1){
    			Gdx.app.log("lem", "done");
    			unitCounter = 0;
    			whosAttacking = PLAYER_TURN;
    		}
		}

    }
    
    
  

}
