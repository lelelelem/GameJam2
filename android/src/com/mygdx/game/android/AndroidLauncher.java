package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.android.volley.VolleyUtil;

public class AndroidLauncher extends AndroidApplication {
    private VolleyUtil volleyUtil;
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VolleyUtil.init(this);
		volleyUtil = VolleyUtil.getInstance();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(volleyUtil), config);
	}
}
