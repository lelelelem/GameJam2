package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.mygdx.interfaces.AnimationListener;

public class GJAnimatingActor extends GJClickableActor{
	private AnimationListener animationListener;
	private boolean isAnimating = false;

	private SpriteBatch sp = new SpriteBatch();
	private float expired = 0.0f;
	private boolean playAnimation = false;

	private int movement = 0;

	//TODO: change to enum
	private static final int IDLE = 0;
	private static final int ATTACKING = 1;
	private static final int HURT = 2;

	protected TextureRegion idle;
	
	protected TextureAtlas atkSheet;
	protected TextureAtlas hurtSheet;
	protected TextureAtlas deadSheet;

	protected TextureRegion[] atkFrames;
	protected TextureRegion[] hurtFrames;
	protected TextureRegion[] deadFrames;
	
	protected Animation attack;
	protected Animation hurt;
	
	public GJAnimatingActor(TextureRegion image) {
		super(image);
	}

	@Override
	public void clickAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (movement == IDLE){
			texture = atkFrames[0];
		}
		else if (movement == ATTACKING) {
			playAnimation(attack);
		}
		else if (movement == HURT) {
			playAnimation(hurt);
		}
		
		super.draw(batch, parentAlpha);
	}
	
	public void playAnimation(Animation animation){
		expired += Gdx.graphics.getDeltaTime();
		texture = animation.getKeyFrame(expired,false);
		if (animation.getAnimationDuration()<=expired) {
			if (animationListener!=null)
				animationListener.onFinish();
			expired = 0.0f;
			isAnimating = false;
			movement = IDLE;
		}
	}

	public void updateListener(AnimationListener animationListener){
		this.animationListener = animationListener;
	}
	

	public void playAttackAnimation() {
		isAnimating = true;
		attack = new Animation(0.05f, atkFrames);
		attack.setPlayMode(PlayMode.NORMAL);
		movement = ATTACKING;
	}

	public boolean isAnimating() {
		return isAnimating;
	}
	
	public void playHurtAnimation(){
		isAnimating = true;
		hurt = new Animation(0.05f, hurtFrames);
		hurt.setPlayMode(PlayMode.NORMAL);
		movement = HURT;
	}
	
	public void loadAnimationFrames(TextureRegion[] textureFrames, TextureAtlas atlas){
		for (int x = 0; x<textureFrames.length; x++){
			textureFrames[x] = atlas.findRegion(Integer.toString(x + 1));
		}
	}
	

}
