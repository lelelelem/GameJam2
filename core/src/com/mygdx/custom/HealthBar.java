package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;

public class HealthBar extends Group {
    
    private GJActor healthbarOn;
    private GJActor healthbarOff;
    
    private int perfectHealth;
    
    public HealthBar(){
        TextureAtlas healthBarAtlas = new TextureAtlas(Gdx.files.internal("healthbar.pack"));
        healthbarOn = new GJActor(healthBarAtlas.findRegion("healthbar_on"));
        healthbarOff = new GJActor(healthBarAtlas.findRegion("healthbar_off"));
        
        this.setHeight(healthbarOff.getHeight());
        this.setWidth(healthbarOff.getWidth());
        
        this.addActor(healthbarOff);
        this.addActor(healthbarOn);
    }
    
    public void updateHP(float current_hp){
        Gdx.app.log("lem", "%"+(current_hp/(float)perfectHealth));
        healthbarOn.setScaleX(current_hp/(float)perfectHealth);
    }

    public int getPerfectHealth() {
        return perfectHealth;
    }

    public void setPerfectHealth(int perfectHealth) {
        this.perfectHealth = perfectHealth;
    }
    
    
    
    

}
