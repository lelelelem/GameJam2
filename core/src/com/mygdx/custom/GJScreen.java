package com.mygdx.custom;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

public class GJScreen implements Screen {

    protected Stage stage;
    protected MyGdxGame game;
    
    public GJScreen(MyGdxGame game){
        this.game = game;    
        stage = new Stage(new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT));
    }
    
    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
    }
    
    public Stage getStage(){
        return stage;
    }

}
