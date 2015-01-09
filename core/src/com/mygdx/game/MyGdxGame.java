package com.mygdx.game;

import nativeUtils.NativeInterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.screen.GJAssetmanager;
import com.mygdx.screen.MainMenu;

public class MyGdxGame extends Game {

    public static final int HEIGHT = 480;
    public static final int WIDTH = 720;
    public GJVolley volleyUtil;
    private NativeInterface nativeSupportInterface;
    
    
    public MyGdxGame(GJVolley volleyUtil, NativeInterface nativeSupportInterface){
        this.setNativeSupportInterface(nativeSupportInterface);
        this.volleyUtil = volleyUtil;
        GJAssetmanager.init();
    }
    
	@Override
	public void create () {
		this.setScreen(new MainMenu(this));
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

    public NativeInterface getNativeSupportInterface() {
        return nativeSupportInterface;
    }

    public void setNativeSupportInterface(NativeInterface nativeSupportInterface) {
        this.nativeSupportInterface = nativeSupportInterface;
    }
}
