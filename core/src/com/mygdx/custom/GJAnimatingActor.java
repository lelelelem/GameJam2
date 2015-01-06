
package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.interfaces.AnimationListener;


public  abstract class GJAnimatingActor extends GJClickableActor {
    private AnimationListener animationListener;

    private boolean isAnimating = false;

    private int status = 0;

    public static final int ALIVE = 0;

    public static final int ISDEAD = 1;

    private float expired = 0.0f;

    private int movement = 0;

    // TODO: change to enum
    private static final int IDLE = 0;

    private static final int ATTACKING = 1;

    private static final int HURT = 2;

    private static final int DEAD = 3;

    protected TextureRegion idle;

    protected TextureAtlas atkSheet;

    protected TextureAtlas hurtSheet;

    protected TextureAtlas deadSheet;

    protected TextureRegion[] atkFrames;

    protected TextureRegion[] hurtFrames;

    protected TextureRegion[] deadFrames;
    protected TextureRegion[] attackEffect;

    protected Animation attack;

    protected Animation hurt;

    protected Animation dead;
    protected Animation effect;
    

    public GJAnimatingActor(TextureRegion image) {
        super(image);
    }
    
    protected void update(){
        TextureAtlas effectAtlas = null;
        switch(effectType()){
        case 1:
            effectAtlas = new TextureAtlas(Gdx.files.internal("effects/sword.pack"));
            break;
        case 2:
            effectAtlas = new TextureAtlas(Gdx.files.internal("effects/fire.pack"));
            break;
        case 3:
            effectAtlas = new TextureAtlas(Gdx.files.internal("effects/arrow.pack"));
            break;
        }
        
        attackEffect = new TextureRegion[effectAtlas.getRegions().size];
        loadAnimationFrames(attackEffect, effectAtlas);
        
        effect = new Animation(0.15f, attackEffect);
        effect.setPlayMode(PlayMode.NORMAL);
    }

    @Override
    public void clickAction() {
        // TODO Auto-generated method stub

    }
    
    protected abstract int effectType();
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (movement == IDLE) {
            if (status != ISDEAD) {
                texture = idle;
            }
        } else if (movement == ATTACKING) {
            playAnimation(attack);
        } else if (movement == HURT) {
            playAnimation(hurt);
            
        } else if (movement == DEAD) {
            playAnimation(dead);
        }

        super.draw(batch, parentAlpha);
    }

    public void playAnimation(Animation animation) {
        expired += Gdx.graphics.getDeltaTime();
        texture = animation.getKeyFrame(expired, false);

            this.setWidth(texture.getRegionWidth());
            this.setHeight(texture.getRegionHeight());

        if (animation.getAnimationDuration() <= expired) {
            if (animationListener != null && animation.equals(attack)) {
                animationListener.onFinish();
            } else if (animation.equals(dead)) {
                 
                this.addAction(Actions.fadeOut(0.0f));
            }

            expired = 0.0f;
            isAnimating = false;
            movement = IDLE;
        }
    }
    
    

    public void updateListener(AnimationListener animationListener) {
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

    public void playDeadAnimation() {
        isAnimating = true;
        dead = new Animation(0.05f, deadFrames);
        dead.setPlayMode(PlayMode.NORMAL);
        movement = DEAD;
    }

    public void playHurtAnimation() {
        isAnimating = true;
        hurt = new Animation(0.05f, hurtFrames);
        hurt.setPlayMode(PlayMode.NORMAL);
        movement = HURT;
    }

    public void loadAnimationFrames(TextureRegion[] textureFrames, TextureAtlas atlas) {
        for (int x = 0; x < textureFrames.length; x++) {
            textureFrames[x] = atlas.findRegion(Integer.toString(x + 1));
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public Animation getAttackEffectAnimation(){
        return effect;
    }

}
