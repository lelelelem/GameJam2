package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;

public class GJEnemy extends GJAnimatingActor {
    
    public EnemyData enemyData;
    
	public GJEnemy(TextureRegion image, EnemyData enemyData) {
		super(image);
		
		this.enemyData = enemyData;
		
		idle = image;
		update();
		hurtSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/hurt.pack");
		atkSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/attack.pack");
		deadSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/dead.pack");

		atkFrames = new TextureRegion[atkSheet.getRegions().size];
		hurtFrames = new TextureRegion[hurtSheet.getRegions().size];
		deadFrames = new TextureRegion[deadSheet.getRegions().size];

        loadAnimationFrames(atkFrames, atkSheet);
        loadAnimationFrames(hurtFrames, hurtSheet);
        loadAnimationFrames(deadFrames, deadSheet);
	}
    
    public EnemyData getEnemyData(){
        return enemyData;
    }
    
    @Override
    public void loadAnimationFrames(TextureRegion[] textureFrames, TextureAtlas atlas) {
        for (int x = 0; x < textureFrames.length; x++) {
            textureFrames[x] = atlas.findRegion(Integer.toString(x + 1));
            textureFrames[x].flip(true, false);
        }
    }

    @Override
    protected int effectType() {
        return enemyData.getAtkEffect();
    }
    
    
   
}
