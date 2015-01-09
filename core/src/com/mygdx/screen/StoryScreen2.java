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

public class StoryScreen2 extends GJScreen{

    private TextureAtlas backstoryAtlas;
    
    private GJActor background;
    private GJActor textBg;
    private GJActor arnaund;
    private GJActor fendrel;
    
    private SHText name;
    private SHText saying;
    private SHText title;
    private int ctr = 0;
    
    public StoryScreen2(MyGdxGame game) {
        super(game);
        
        backstoryAtlas = new TextureAtlas(Gdx.files.internal("backstory.pack"));
        
        background = new GJActor(backstoryAtlas.findRegion("back2"));
        textBg = new GJActor(backstoryAtlas.findRegion("textback"));
        
        arnaund = new GJActor(backstoryAtlas.findRegion("arnaund"));
        fendrel = new GJActor(backstoryAtlas.findRegion("fendrel"));
        
        title = new SHText("", SHText.Size.XXLARGE, SHText.Font.BPREPLAY, Color.BLACK);
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
       
       title.addAction(Actions.sequence(Actions.fadeOut(0.0f), Actions.fadeIn(0.5f), Actions.fadeOut(0.0f), Actions.run(new Runnable() {
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
            saying.setText("What Fools! This army is no match against our strength.");
            break;
        case 1:
            arnaund.setVisible(false);
            fendrel.setVisible(true);
            name.setText("Fendrel");
            saying.setText("Do not be too full of yourself Arnaund. More are incoming!");
            break;
        case 2:
            fendrel.setVisible(false);
            arnaund.setVisible(true);
            name.setText("Arnaund");
            saying.setText("Let them come! For today we make this kingdom ours!");
            break;
        case 3:
            game.setScreen(new ToBeContinued(game));
        }
        saying.setWidth(textBg.getWidth());
        saying.setWrap(true);
        name.setPosition(textBg.getX()+5.0f, textBg.getTop() - 5.0f - name.getHeight());
        saying.setPosition(name.getX(), (textBg.getY()+(textBg.getHeight()/2)) - (saying.getHeight()/2+10.0f) );
        ctr++;
    }
    
}
