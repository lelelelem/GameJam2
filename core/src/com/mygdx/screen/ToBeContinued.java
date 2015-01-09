package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.custom.GJActor;
import com.mygdx.custom.GJScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.util.SHText;

public class ToBeContinued extends GJScreen{

    private SHText toBeContinued;
    private SHText voteAndSupportUs;
    private GJActor toMain;
    private MyGdxGame game;
    
    public ToBeContinued(MyGdxGame game) {
        super(game);
        this.game = game;
        
        toBeContinued = new SHText("To Be Continued..", SHText.Size.XXLARGE, SHText.Font.BEASTFORMER, Color.BLACK);
        toBeContinued.setPosition(MyGdxGame.WIDTH/2 - toBeContinued.getWidth()/2, MyGdxGame.HEIGHT - toBeContinued.getHeight() - 10.0f);
        voteAndSupportUs =  new SHText("Vote us for Possible Additonal Features! \nGatcha Box (Summon Unit)\nMap View\nInventory System\nLevel Up System and lots more!", SHText.Size.XXLARGE, SHText.Font.BEASTFORMER, Color.BLACK);
        voteAndSupportUs.setWidth(MyGdxGame.WIDTH-200);
        voteAndSupportUs.setWrap(true);
        voteAndSupportUs.setAlignment(Align.center);
        
        toMain = new GJActor(new TextureAtlas(Gdx.files.internal("buttons.pack")).findRegion("menu"));
        toMain.addListener(new ClickListener(){
           @Override
        public void clicked(InputEvent event, float x, float y) {
               toScreen();
        }
        });
        
        toBeContinued.addAction(Actions.sequence(Actions.fadeOut(0.0f), Actions.fadeIn(0.8f), Actions.run(new Runnable() {
            @Override
            public void run() {
                voteAndSupportUs.addAction(Actions.fadeIn(0.5f));
            }
        })));
        
        voteAndSupportUs.addAction(Actions.fadeOut(0.0f));
        
        stage.addActor(toBeContinued);
        stage.addActor(voteAndSupportUs);
        stage.addActor(toMain);
    }
    
    private void toScreen(){
        game.setScreen(new MainMenu(game));
    }

}
