package com.mygdx.screen;

import nativeUtils.NativeInterface.OnResponseListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.data.EnemyData;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.interfaces.VolleyResultListener;
import com.mygdx.util.SHText;

public class NetworkLoadingScreen extends GJScreen implements VolleyResultListener, OnResponseListener{
    
	private boolean hasLoaded = false;  
    private TextureAtlas loadingAtlas;
    private GJActor bg;
    private SHText loading;
    private GJActor circle;
    private TextureRegion[] animationFrame;
    private Animation loadingCircle;
    private boolean alreadyLoading = false;
    private float expired = 0.0f;
    
    
    public NetworkLoadingScreen(MyGdxGame game) {
        super(game);
        
        loadingAtlas = new TextureAtlas(Gdx.files.internal("loading.pack"));
        animationFrame = new TextureRegion[loadingAtlas.getRegions().size-1];
        bg = new GJActor(loadingAtlas.findRegion("background"));
        bg.setScale(MyGdxGame.WIDTH/bg.getWidth(), MyGdxGame.HEIGHT/bg.getHeight());
        game.getVolleyInstance().createDAO(GJVolley.url, this, this);
        
        loading = new SHText("Loading 0%", 15, 25, SHText.Size.XXLARGE, Color.WHITE);
        loading.setY(0.0f);
        
        for (int x =0; x<loadingAtlas.getRegions().size-1;x++){
            animationFrame[x] = loadingAtlas.findRegion(Integer.toString(x+1));
        }
        
        circle = new GJActor(animationFrame[0]);
        
        loadingCircle = new Animation(0.1f, animationFrame);
        loadingCircle.setPlayMode(PlayMode.LOOP);
        circle.setPosition(loading.getX()+loading.getWidth()+2.0f, (loading.getY()+(loading.getHeight()/2))-circle.getHeight()/2);
        
        stage.addActor(bg);
        stage.addActor(loading);
        stage.addActor(circle);
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        expired+=delta;

        if (hasLoaded){
            loading.setText("Loading " + ((int) (GJAssetmanager.get().getProgress() * 100)) + "%");
            
            circle.setTexture(loadingCircle.getKeyFrame(expired));
            circle.setPosition(loading.getX()+loading.getWidth()+2.0f, (loading.getY()+(loading.getHeight()/2))-circle.getHeight()/2);
            if (GJAssetmanager.get().update()){
                game.setScreen(new PreBattleScreen(game, game.getVolleyInstance().getUnits(), game.getVolleyInstance().getStages()));
            }
        }
    }
    
    public void hasLoaded(){
        for (UnitData data: game.getVolleyInstance().getUnits()){
            GJAssetmanager.get().loadAnimationPacks("units/"+data.getUnit_name());
        }
        
        for (StageData data: game.getVolleyInstance().getStages()){
            for (EnemyData enemy:data.getEnemies()){
                GJAssetmanager.get().loadAnimationPacks("enemies/"+enemy.getUnit_name());    
            }
        }
        
        GJAssetmanager.get().loadTextureAtlas("effects/sword.pack");
        GJAssetmanager.get().loadTextureAtlas("effects/fire.pack");
        GJAssetmanager.get().loadTextureAtlas("effects/arrow.pack");
   
        hasLoaded = true;
        
    }

    @Override
    public void onError() {
        game.getNativeSupportInterface().showDialog(this);
    }

    @Override
    public void doAction() {
        game.getVolleyInstance().createDAO(GJVolley.url, this, this);
    }

}
