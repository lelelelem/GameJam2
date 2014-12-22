package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.UnitData;

public class GJUnit extends GJClickableActor {
    
    public UnitData unitData;
    
    public GJUnit(TextureRegion image, UnitData unitData) {
        super(image);
        this.unitData = unitData;
    }
    
    public UnitData getUnitData(){
        return unitData;
    }
    
    @Override
    public void clickAction() {   
    }
    


}
