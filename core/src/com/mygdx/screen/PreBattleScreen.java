package com.mygdx.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJClickableActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.custom.GJThumbnail;
import com.mygdx.custom.GJUnitGrid;
import com.mygdx.custom.GJThumbnail.GJClickListenerInterface;
import com.mygdx.custom.GJUnit;
import com.mygdx.custom.TargetGrid;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.final_values.AssetList;
import com.mygdx.game.MyGdxGame;

public class PreBattleScreen extends GJScreen{
    private GJThumbnail unitThumbnail[];
    
    private TextureAtlas ectAtlas;
    
    private GJActor background;
    private GJClickableActor battleIcon;
    
    private TargetGrid unitGrid;
    private TargetGrid enemyGrid;
    
    private Group unitThumbs;
    
    private GJUnit tempUnit;
    private boolean hasSelected = false;
    private MyGdxGame game;
    
    private List<StageData> stages;
    private List<UnitData> unitData;
    
    
    public PreBattleScreen(MyGdxGame game, List<UnitData> unitData, List<StageData> stages) {
        super(game);
        
       
        this.stages = stages;
        this.game = game;
        this.unitData = unitData;
        
        
        unitThumbs = new Group();
        unitThumbnail = new GJThumbnail[this.unitData.size()];
        
       
        unitGrid = new TargetGrid(this);
        
        enemyGrid = new  TargetGrid(this);
        
        ectAtlas = new TextureAtlas(AssetList.Assets.ATLAS_GAMESCREEN.getPath());
        
        background = new GJActor(ectAtlas.findRegion(AssetList.Assets.ASSET_BG_FIELD.getPath()));
        background.setWidth((MyGdxGame.WIDTH/background.getWidth())*background.getWidth());
        background.setHeight((MyGdxGame.HEIGHT/background.getHeight())*background.getHeight());
        

        battleIcon = new GJClickableActor(new TextureAtlas(Gdx.files.internal("buttons.pack")).findRegion("battle")){
           @Override
            public void clickAction() {   
               toBattle();
            }
           };
           battleIcon.setScale(1.3f);
        
        int i = 0;
        
        //setup unit thumbs
        for(UnitData unit: this.unitData){
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
        
    
        stage.addActor(background);
        stage.addActor(unitGrid);
        stage.addActor(enemyGrid);
        stage.addActor(unitThumbs);
        stage.addActor(battleIcon);
        
        enemyGrid.setX(0+20.0f);
        enemyGrid.setY(80.0f);
        
        unitGrid.setX(MyGdxGame.WIDTH-unitGrid.getWidth());
        unitGrid.setY(80.0f);
      
        
        float spacing = 0.0f;
        
        i =0;
        
        for(GJThumbnail thumb:unitThumbnail){
            thumb.setScale(0.2f);
            thumb.setWidth(thumb.getWidth()*thumb.getScaleX());
            thumb.setHeight(thumb.getHeight()*thumb.getScaleY());
            thumb.setX(spacing);
            spacing= thumb.getX() + (thumb.getWidth()*thumb.getScaleX()+50.0f); 
        }
      
        enemyGrid.refreshGrid(0);
        
        unitThumbs.setWidth(spacing);
        unitThumbs.setX(MyGdxGame.WIDTH/2-unitThumbs.getWidth()/2);
        unitThumbs.setY(20.0f);
        
         spacing = 0.0f;
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
        boolean hasNoUnits = true;
        
        for (Actor grid:unitGrid.getChildren()){
            if(((GJUnitGrid)(grid)).getUnit()!=null){
                hasNoUnits = false;
                break;
            }
        }
        
        if (hasNoUnits){
            placeInRandom();
        }
        
        game.setScreen(new BattleScreen(game, unitGrid, enemyGrid, background, stages));
    }
    
    private void placeInRandom(){
        List<Integer> numberBlock = new ArrayList<Integer>();
        int i=0;
        
        for (Actor actor:unitGrid.getChildren()){
            numberBlock.add(i++);
        }
        
        for(int x=0; x<unitThumbnail.length;x++ ){
            int random = (int)(Math.random()*numberBlock.size());
            Integer toRemove = numberBlock.get(random);
            GJUnitGrid templ = (GJUnitGrid)unitGrid.getChildren().get(toRemove);
            tempUnit = unitThumbnail[x].getUnit();
            templ.addUnit();
            numberBlock.remove(toRemove);   
        }
        
        
    }
    
}
