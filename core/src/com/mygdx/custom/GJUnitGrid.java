package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.final_values.AssetList;
import com.mygdx.interfaces.PressCallback;
import com.mygdx.screen.PreBattleScreen;

public class GJUnitGrid extends Group{

    public static final int UNTOGGLE = 0;
    public static final int TOGGLE = 1;
    
    private GJActor targetGrid;
    private TextureRegion targetCircle;
    private TextureAtlas atlas;
    private boolean isPressed = false;
    private PreBattleScreen preBattle;
    private GJUnit unit;
    private String range[];
    
    public GJUnitGrid(final String name, PreBattleScreen preBattle) {
        this.preBattle = preBattle;
        atlas = new TextureAtlas(AssetList.Assets.ATLAS_GAMESCREEN.getPath());
        targetCircle = atlas.findRegion(AssetList.Assets.ASSET_TARGET.getPath());
        
        targetGrid = new GJActor(targetCircle);
        this.addActor(targetGrid);
        this.setWidth(targetCircle.getRegionWidth());
        this.setHeight(targetCircle.getRegionHeight());
        this.setName(name);
        
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (unit==null)
                    addUnit();
                else{
                    callClearGrid();
                }
            }
        });
    }
    
    public boolean isPressed(){
        return this.isPressed;
    }
    
    @Override
    public void act(float delta) {
    
        super.act(delta);
    }
    
    public void addUnit(){
        if (preBattle.getTempUnit() != null){
            callClearGrid();
            unit = preBattle.getTempUnit();
            this.addActor(unit);
            unit.setScale(0.2f, 0.2f);
            unit.setX(this.getWidth()/2 - unit.getWidth()*unit.getScaleX()/2);
            unit.setY(this.getHeight()/2);
            unit.setName("unit");
            plotTargetRange();
        }
    }
    
    public void toggle(){
        this.setVisible(true);
    }
    
    public void untoggle(){
        this.setVisible(false);
    }
    
    private void plotTargetRange(){
        if ( unit == null){
           preBattle.getEnemyGrid().refreshGrid(UNTOGGLE);
        }
        
        range= unit.getUnitData().getTarget_range().split(",");
        Gdx.app.log("lem", "range current "+range.length);
        preBattle.getEnemyGrid().refreshGrid(TOGGLE);
    }
    
  
    public GJUnit getUnit(){
        return unit;
    }
    
    public void callClearGrid(){
        if (preBattle.getTempUnit()!=null){
            preBattle.getUnitGrid().clearGrid(preBattle.getTempUnit().getUnitData().getUnit_name(), this.getName());
        }
    }
    
    public void clearUnit(){
        if (this.findActor("unit")!=null){
            this.findActor("unit").remove();
        }
        
        unit = null;
    }
    
    public String[] getRange(){
        return range;
    }
}
