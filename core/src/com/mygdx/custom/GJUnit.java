package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.data.UnitData;

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
		deadFrames = new TextureRegion[deadSheet.getRegions().size];

		loadAnimationFrames(atkFrames, atkSheet);
		loadAnimationFrames(hurtFrames, hurtSheet);
		loadAnimationFrames(deadFrames, deadSheet);
		
	}

	public UnitData getUnitData() {
		return unitData;
	}
	
	 @Override
	    public void loadAnimationFrames(TextureRegion[] textureFrames, TextureAtlas atlas) {
	        for (int x = 0; x < textureFrames.length; x++) {
	            textureFrames[x] = atlas.findRegion(Integer.toString(x + 1));
	        }
	    }
}
