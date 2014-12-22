package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;

public class GJEnemy extends GJClickableActor {
    
    public EnemyData enemyData;
    
    public GJEnemy(TextureRegion image, EnemyData enemyData) {
        super(image);
        this.enemyData = enemyData;
    }
    
    public EnemyData getEnemyData(){
        return enemyData;
    }
    
    @Override
    public void clickAction() {   
    }


}
