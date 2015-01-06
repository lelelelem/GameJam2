package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJClickableActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;

public class MapScreen extends GJScreen
{
    private TextureAtlas mapAtlas;
    private GJActor mapBG;
    private GJClickableActor point;
    
    public MapScreen(MyGdxGame game) {
        super(game);
        
        mapAtlas = new TextureAtlas(Gdx.files.internal("world.pack"));
        
        mapBG = new GJActor(mapAtlas.findRegion("map"));
        mapBG.setScale(MyGdxGame.WIDTH/mapBG.getWidth(), MyGdxGame.HEIGHT/mapBG.getHeight());
        
        point = new GJClickableActor(mapAtlas.findRegion("point")) {
            @Override
            public void clickAction() {
                
            }
        };
        
        stage.addActor(mapBG);
        stage.addActor(point);
    }

}
