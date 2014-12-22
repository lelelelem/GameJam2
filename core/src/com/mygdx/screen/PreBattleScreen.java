package com.mygdx.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJClickableActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.custom.GJThumbnail;
import com.mygdx.custom.GJThumbnail.GJClickListenerInterface;
import com.mygdx.custom.GJUnit;
import com.mygdx.custom.TargetGrid;
import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;
import com.mygdx.final_values.AssetList;
import com.mygdx.game.MyGdxGame;

public class PreBattleScreen extends GJScreen{
    private GJThumbnail unitThumbnail[];
    private GJThumbnail enemyThumbnail[];
    
    
    private TextureAtlas preBattleAtlas;
    private TextureAtlas ectAtlas;
    
    private GJActor background;
    private GJClickableActor battleIcon;
    private TargetGrid unitGrid;
    private TargetGrid enemyGrid;
    
    private Group unitThumbs;
    private Group enemyThumbs;
    
    private GJUnit tempUnit;
    private boolean hasSelected = false;
    private MyGdxGame game;
    
    
    public PreBattleScreen(MyGdxGame game, List<UnitData> unitData, List<EnemyData> enemyData) {
        super(game);
        
        this.game = game;
        
        unitThumbs = new Group();
        unitThumbnail = new GJThumbnail[unitData.size()];
        
        enemyThumbs = new Group();
        enemyThumbnail = new GJThumbnail[enemyData.size()];
       
        unitGrid = new TargetGrid(this);
        enemyGrid = new  TargetGrid(this);
        
        ectAtlas = new TextureAtlas(AssetList.Assets.ATLAS_ETC.getPath());
        preBattleAtlas = new TextureAtlas(AssetList.Assets.ATLAS_GAMESCREEN.getPath());
        background = new GJActor(ectAtlas.findRegion(AssetList.Assets.ASSET_BG_FIELD.getPath()));
        background.setWidth((MyGdxGame.WIDTH/background.getWidth())*background.getWidth());
        background.setHeight((MyGdxGame.HEIGHT/background.getHeight())*background.getHeight());
        
        
        battleIcon = new GJClickableActor(ectAtlas.findRegion(AssetList.Assets.ASSET_BATTLE_ICON.getPath())){
           @Override
            public void clickAction() {
               
               for(GJThumbnail thumb:enemyThumbnail){
                   enemyGrid.addToSpecificGrid(thumb.getEnemy().getEnemyData().getCoordinates(), thumb.getEnemy());
               }
               
               toBattle();
            }
           };
           
           battleIcon.setScale(0.2f);
        
        int i = 0;
        
        for(UnitData unit: unitData){
            unitThumbnail[i] = new GJThumbnail(unit, new GJClickListenerInterface() {
                @Override
                public void onFocus() {
                    hasSelected = true;
                }
                
                @Override
                public void onDeFocus() {
                    hasSelected = false;
                }
            });
            unitThumbs.addActor(unitThumbnail[i]);
            i++;
        }
        
        i=0;
        
        for(EnemyData enemy: enemyData){
            enemyThumbnail[i] = new GJThumbnail(enemy);
            enemyThumbs.addActor(enemyThumbnail[i]);
            i++;
        }
        
        
        stage.addActor(background);
        stage.addActor(unitGrid);
        stage.addActor(enemyGrid);
        stage.addActor(unitThumbs);
        stage.addActor(enemyThumbs);
        stage.addActor(battleIcon);
        
        enemyGrid.setX(0);
        enemyGrid.setY(80.0f);
        
        unitGrid.setX(MyGdxGame.WIDTH-unitGrid.getWidth());
        unitGrid.setY(80.0f);
        
        for (Actor actor:enemyGrid.getChildren()){
            actor.setVisible(false);
        }
        
        float spacing = 0.0f;
        
        i =0;
        
        for(GJThumbnail thumb:unitThumbnail){
            thumb.setScale(0.2f);
            thumb.setWidth(thumb.getWidth()*thumb.getScaleX());
            thumb.setHeight(thumb.getHeight()*thumb.getScaleY());
            thumb.setX(spacing);
            spacing= thumb.getX() + (thumb.getWidth()*thumb.getScaleX()+50.0f); 
        }
        
        
        unitThumbs.setWidth(spacing);
        unitThumbs.setX(MyGdxGame.WIDTH/2-unitThumbs.getWidth()/2);
        unitThumbs.setY(20.0f);
        
         spacing = 0.0f;
        
        for(GJThumbnail thumb:enemyThumbnail){
            thumb.setScaleX((unitThumbnail[0].getWidth())/thumb.getWidth());
            thumb.setScaleY((unitThumbnail[0].getHeight())/thumb.getHeight());
            
            thumb.setWidth(thumb.getWidth()*thumb.getScaleX());
            thumb.setHeight(thumb.getHeight()*thumb.getScaleY());
            
            thumb.setX(spacing);
            spacing= thumb.getX() + (thumb.getWidth()*thumb.getScaleX()+50.0f);
        }
        
        enemyThumbs.setWidth(spacing);
        enemyThumbs.setX(0);
        enemyThumbs.setY(MyGdxGame.HEIGHT/2);
      
        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
       
        if(hasSelected){
            for (GJThumbnail thumb:unitThumbnail){
                if (!thumb.isPressed()){
                    thumb.setActivated(false);
                }
                else{
                    if (tempUnit==null)
                        tempUnit =thumb.getUnit();
                }
            }
        }
        else{
            tempUnit = null;
            for (GJThumbnail thumb:unitThumbnail){
                thumb.setActivated(true);
            }
        }
    }
    
    public GJUnit getTempUnit(){
        return tempUnit;
    }
    
    public TargetGrid getEnemyGrid(){
        return enemyGrid;
    }

    public TargetGrid getUnitGrid(){
        return unitGrid;
    }
    
    private void toBattle(){
        game.setScreen(new BattleScreen(game, unitGrid, enemyGrid, background));
    }
}
