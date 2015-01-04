package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.util.SHText;

public class NetworkLoadingScreen extends GJScreen{
    
	private boolean hasLoaded = false;  
    private TextureAtlas loadingAtlas;
    private GJActor bg;
    private SHText loading;
    private int ctr = 0;
    
    private float elapsed = 0.0f;
	
    public NetworkLoadingScreen(MyGdxGame game) {
        super(game);
        loadingAtlas = new TextureAtlas(Gdx.files.internal("loading.pack"));
        bg = new GJActor(loadingAtlas.findRegion("background"));
        game.getVolleyInstance().createDAO(GJVolley.url, this);
        
        loading = new SHText("Loading.", 15, 25, SHText.Size.XXLARGE, Color.BLACK);
        stage.addActor(bg);
        stage.addActor(loading);
    }
    
    @Override
    public void render(float delta) {
    	elapsed += delta;
    	Gdx.app.log("lem", "int");
    	if (elapsed >= Gdx.graphics.getDeltaTime()){
    		Gdx.app.log("lem", ctr+"");
    		switch(ctr){
    		case 0:
    			loading.setText("Loading..");
    			ctr++;
    			break;
    		case 1:
    			loading.setText("Loading...");
    			ctr++;
    			break;
    		case 2:
    			loading.setText("Loading.");
    			ctr=0;
    			break;
    		}
    		elapsed = 0.0f;
    	}
    	
    	
        if (hasLoaded){
            game.setScreen(new PreBattleScreen(game, game.getVolleyInstance().getUnits(), game.getVolleyInstance().getEnemy()));
        }
            
        super.render(delta);
    }
    
    public void hasLoaded(){
        hasLoaded = true;
    }

}
