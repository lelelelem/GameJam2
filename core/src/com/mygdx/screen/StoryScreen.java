package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.util.SHText;

public class StoryScreen extends GJScreen{

    private TextureAtlas backstoryAtlas;
    
    private GJActor background;
    private GJActor textBg;
    private GJActor arnaund;
    private GJActor fendrel;
    
    private SHText name;
    private SHText saying;
    private SHText title;
    
    private int ctr = 0;
    
    public StoryScreen(MyGdxGame game) {
        super(game);
        
        backstoryAtlas = new TextureAtlas(Gdx.files.internal("backstory.pack"));
        
        background = new GJActor(backstoryAtlas.findRegion("background"));
        textBg = new GJActor(backstoryAtlas.findRegion("textBack"));
        
        arnaund = new GJActor(backstoryAtlas.findRegion("Arnaud"));
        fendrel = new GJActor(backstoryAtlas.findRegion("Fendrel"));
        
        title = new SHText("The Kingdom of Vlad", SHText.Size.XXLARGE, SHText.Font.BPREPLAY, Color.BLACK);
        name = new SHText("Name", SHText.Size.MEDIUM, SHText.Font.BPREPLAY, Color.WHITE);
        saying = new SHText("Churva", SHText.Size.MEDIUM, SHText.Font.BPREPLAY, Color.WHITE);
        
        saying.setWidth(textBg.getWidth());
        
        background.setScale(MyGdxGame.WIDTH/background.getWidth(), MyGdxGame.HEIGHT/background.getHeight());
        
        textBg.setPosition(MyGdxGame.WIDTH/2 - textBg.getWidth()/2, 10.0f);
        arnaund.setPosition(MyGdxGame.WIDTH-arnaund.getWidth(),0.0f);
        
        title.setPosition(MyGdxGame.WIDTH/2 - title.getWidth()/2, MyGdxGame.HEIGHT- title.getHeight() - 15.0f);
        name.setPosition(textBg.getX()+5.0f, textBg.getTop() - 5.0f - name.getHeight());
        saying.setWrap(true);
        
        stage.addActor(background);
        stage.addActor(fendrel);
        stage.addActor(arnaund);
        stage.addActor(textBg);
        stage.addActor(name);
        stage.addActor(saying);
        stage.addActor(title);
        
       arnaund.setVisible(false);
       fendrel.setVisible(false);
       textBg.setVisible(false);
       name.setVisible(false);
       saying.setVisible(false);
       
       title.addAction(Actions.sequence(Actions.fadeOut(0.0f), Actions.fadeIn(1.5f), Actions.fadeOut(1.0f), Actions.run(new Runnable() {
        @Override
        public void run() {
           arnaund.setVisible(true);
           textBg.setVisible(true);
           name.setVisible(true);
           saying.setVisible(true);
           doComicStrip();
           stage.addListener(new ClickListener(){
               @Override
            public void clicked(InputEvent event, float x, float y) {
              doComicStrip();
            }
           });
        }
    })));
       
   Gdx.input.setInputProcessor(stage);
    }
    
    private void doComicStrip(){
        switch (ctr){
        case 0:
            name.setText("Arnaund");
            saying.setText("The Kingdom of Vlad. The smallest western kingdom.");
            break;
        case 1:
            arnaund.setVisible(false);
            fendrel.setVisible(true);
            name.setText("Fendrel");
            saying.setText("And the only kingdom we can possibly conquer.");
            break;
        case 2:
            saying.setText("We would be fools to attack Vorin directly with the number and quality of units we have.");
            break;
        case 3:
            fendrel.setVisible(false);
            arnaund.setVisible(true);
            name.setText("Arnaund");
            saying.setText("That bastard Malak, we will make him pay! He stole our kingdom. He will regret keeping us alive!");
            break;
        case 4:
            arnaund.setVisible(false);
            name.setText("Soldier");
            saying.setText("Enemy Units incoming!");
            break;
        case 5:
            arnaund.setVisible(true);
            name.setText("Arnaund");
            saying.setText("There goes our element of surprise. Units get in position!");
            break;
        case 6:
            game.setScreen(new NetworkLoadingScreen(game));
        }
        saying.setWidth(textBg.getWidth());
        saying.setWrap(true);
        name.setPosition(textBg.getX()+5.0f, textBg.getTop() - 5.0f - name.getHeight());
        saying.setPosition(name.getX(), (textBg.getY()+(textBg.getHeight()/2)) - (saying.getHeight()/2+10.0f) );
        ctr++;
    }
    
}
