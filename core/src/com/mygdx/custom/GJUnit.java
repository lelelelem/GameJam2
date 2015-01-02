package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.UnitData;
import com.mygdx.interfaces.AnimationListener;

public class GJUnit extends GJAnimatingActor {

	public UnitData unitData;

	public GJUnit(TextureRegion image, UnitData unitData) {
		super(image);
		this.unitData = unitData;
		idle = image;
		hurtSheet = new TextureAtlas("units/" + unitData.getUnit_name()
				+ "/hurt.pack");
		atkSheet = new TextureAtlas("units/" + unitData.getUnit_name()
				+ "/attack.pack");
		deadSheet = new TextureAtlas("units/" + unitData.getUnit_name()
				+ "/dead.pack");

		atkFrames = new TextureRegion[atkSheet.getRegions().size];
		hurtFrames = new TextureRegion[hurtSheet.getRegions().size];

		loadAnimationFrames(atkFrames, atkSheet);
		loadAnimationFrames(hurtFrames, hurtSheet);
		
	}

	public UnitData getUnitData() {
		return unitData;
	}
	
	public void loadAnimationFrames(TextureRegion[] textureFrames, TextureAtlas atlas){
		for (int x = 0; x<textureFrames.length; x++){
			textureFrames[x] = atlas.findRegion(Integer.toString(x + 1));
		}
	}

}
