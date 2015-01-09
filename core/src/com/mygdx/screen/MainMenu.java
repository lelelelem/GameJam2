package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;

public class MainMenu extends GJScreen{

    private TextureAtlas mainMenuAtlas;
    private GJActor logo;
    private GJActor background;
    private GJActor start;
    private MyGdxGame game;
    
    public MainMenu(MyGdxGame game) {
        super(game);
        
        this.game = game;
        
        
        
        mainMenuAtlas = new TextureAtlas(Gdx.files.internal("mainmenu.pack"));
        
        logo = new GJActor(mainMenuAtlas.findRegion("logo"));
        background = new GJActor(mainMenuAtlas.findRegion("background"));
        start = new GJActor(new TextureAtlas(Gdx.files.internal("buttons.pack")).findRegion("start"));
        
        stage.addActor(background);
        stage.addActor(logo);
        stage.addActor(start);
        
        background.setScale(MyGdxGame.WIDTH/background.getWidth(), MyGdxGame.HEIGHT/background.getHeight());
        logo.setScaleX(MyGdxGame.WIDTH/logo.getWidth());
        logo.setScaleY(logo.getScaleX());
        logo.setPosition(MyGdxGame.WIDTH/2 - logo.getWidth()*logo.getScaleX()/2, (MyGdxGame.HEIGHT/2 - logo.getHeight()/2)+35.0f);
        start.setScale(1.3f);
        start.setPosition(MyGdxGame.WIDTH/2 - start.getWidth()*start.getScaleX()/2, logo.getY() -start.getHeight()*start.getScaleX()- 110.0f);
        
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
             toStoryScreen();
            }
        });
        
        Gdx.input.setInputProcessor(stage);
    }
    
    private void toStoryScreen(){
        game.setScreen(new StoryScreen(game));
    }
    
    

}
