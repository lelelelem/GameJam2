package com.mygdx.screen;

import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.interfaces.GJVolley;

public class NetworkLoadingScreen extends GJScreen{
    private boolean hasLoaded =false;  
    
    public NetworkLoadingScreen(MyGdxGame game) {
        super(game);
        game.getVolleyInstance().createDAO(GJVolley.url, this);
        
    }
    
    @Override
    public void render(float delta) {
        if (hasLoaded){
            game.setScreen(new PreBattleScreen(game, game.getVolleyInstance().getUnits(), game.getVolleyInstance().getEnemy()));
        }
            
        super.render(delta);
    }
    
    public void hasLoaded(){
        hasLoaded = true;
    }

}
