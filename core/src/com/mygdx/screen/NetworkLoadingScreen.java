package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.data.EnemyData;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.util.SHText;

public class NetworkLoadingScreen extends GJScreen{
    
	private boolean hasLoaded = false;  
    private TextureAtlas loadingAtlas;
    private GJActor bg;
    private SHText loading;
    
    public NetworkLoadingScreen(MyGdxGame game) {
        super(game);
        loadingAtlas = new TextureAtlas(Gdx.files.internal("loading.pack"));
        bg = new GJActor(loadingAtlas.findRegion("background"));
        game.getVolleyInstance().createDAO(GJVolley.url, this);
        
        loading = new SHText("Loading", 15, 25, SHText.Size.XXLARGE, Color.BLACK);
        stage.addActor(bg);
        stage.addActor(loading);
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        if (hasLoaded){
            if (GJAssetmanager.get().update()){
                Gdx.app.log("lem", "done");
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
   
        hasLoaded = true;
        
    }

}
