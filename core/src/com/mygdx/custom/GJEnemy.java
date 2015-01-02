package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;

public class GJEnemy extends GJAnimatingActor {
    
    public EnemyData enemyData;
    
	public GJEnemy(TextureRegion image, EnemyData enemyData) {
		super(image);
		this.enemyData = enemyData;
		this.enemyData = enemyData;
		idle = image;
		hurtSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/hurt.pack");
		atkSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/attack.pack");
		deadSheet = new TextureAtlas("enemies/" + enemyData.getUnit_name()
				+ "/dead.pack");

		atkFrames = new TextureRegion[atkSheet.getRegions().size];
		hurtFrames = new TextureRegion[hurtSheet.getRegions().size];

		loadAnimationFrames(atkFrames, atkSheet);
		loadAnimationFrames(hurtFrames, hurtSheet);
	}
    
    public EnemyData getEnemyData(){
        return enemyData;
    }
   
}
