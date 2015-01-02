package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;
import com.mygdx.interfaces.AnimationListener;

/*Will hold Data 
 * such as:
 * Atlass File name : for creation of Actor (Unit)
 * 
 * */

public class GJThumbnail extends Group{

    private TextureAtlas atlas;
    private GJActor thumbUnit;
    
    //create Separate Actor in the future
    //will hold DAO
    private GJUnit unit;
    
    private GJEnemy enemy;
    
    private float alpha = 1.0f;
    private boolean trigger = false;
    private boolean activated = true;
  
    public interface GJClickListenerInterface{
        public void onFocus();
        public void onDeFocus();
    }
    
    
    private class GJClickListener extends ClickListener{
        private GJClickListenerInterface clickListenerInterface;
        
        public GJClickListener(GJClickListenerInterface clickListenerInterface){
            this.clickListenerInterface =clickListenerInterface;
        }
        
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (activated){
                makeTransparent();
                if (!trigger){
                    clickListenerInterface.onDeFocus();
                }
                else{
                    clickListenerInterface.onFocus();
                }   
            }
            super.clicked(event, x, y);
        }
    }
    
    
    public GJThumbnail(UnitData unitData, GJClickListenerInterface clickListener){
        atlas = new TextureAtlas("units/"+unitData.getUnit_name()+"/preview.pack");
        thumbUnit = new GJActor(atlas.findRegion("thumb"));
        this.setWidth(atlas.findRegion("thumb").getRegionWidth());
        this.setHeight(atlas.findRegion("thumb").getRegionHeight());
        unit = new GJUnit(atlas.findRegion("preview"), unitData);
        unit.setScale(0.13f);
        this.addActor(thumbUnit);
        this.addListener(new GJClickListener(clickListener));
    }
    
    public GJThumbnail(EnemyData enemyData){
        //TODO: change to enemies
        atlas = new TextureAtlas("enemies/"+enemyData.getUnit_name()+"/preview.pack");
        TextureRegion region = atlas.findRegion("thumb");
        TextureRegion enemyRegion = atlas.findRegion("preview");
        enemyRegion.flip(true, false);
        
        this.setWidth(atlas.findRegion("thumb").getRegionWidth());
        this.setHeight(atlas.findRegion("thumb").getRegionHeight());
        
        this.enemy = new GJEnemy(enemyRegion, enemyData);
        enemy.setScale(0.13f);
        region.flip(true, false);
        
        thumbUnit = new GJActor(region);
        this.addActor(thumbUnit);
    }
    
     @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, alpha);
        super.draw(batch, parentAlpha);
    }
     
   private void makeTransparent(){
       if (!trigger)
           alpha = 0.5f;
       else
           alpha = 1.0f;
       
       trigger = !trigger;
   }
   
   public boolean isPressed(){
       return trigger;
   }
   
   public void setActivated(boolean activated){
       this.activated = activated;
   }
   
   public GJUnit getUnit(){
       return unit;
   }
   
   public GJEnemy getEnemy(){
       return enemy;
   }
   
   
}
