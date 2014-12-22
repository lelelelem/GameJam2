package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.screen.NetworkLoadingScreen;
import com.mygdx.screen.PreBattleScreen;

public class MyGdxGame extends Game {

    public static final int HEIGHT = 480;
    public static final int WIDTH = 720;
    public GJVolley volleyUtil;
    
    
    public MyGdxGame(GJVolley volleyUtil){
        this.volleyUtil = volleyUtil;
    }
    
	@Override
	public void create () {
		this.setScreen(new NetworkLoadingScreen(this));
	}

	@Override
	public void render () {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
	}
	
	public GJVolley getVolleyInstance(){
	    return volleyUtil;
	}
}
