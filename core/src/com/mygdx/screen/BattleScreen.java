package com.mygdx.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.custom.GJScreen;
import com.mygdx.custom.TargetGrid;
import com.mygdx.game.MyGdxGame;

public class BattleScreen extends GJScreen{
    
    private TargetGrid unitGrid;
    private TargetGrid enemyGrid;
    
    public BattleScreen(MyGdxGame game,TargetGrid unitGrid, TargetGrid enemyGrid) {
        super(game);
        this.unitGrid = unitGrid;
        this.enemyGrid =enemyGrid;
        
        stage.addActor(unitGrid);
        stage.addActor(enemyGrid);
        
        showEnemyGrid();
    }
    
    private void showEnemyGrid(){
        for(Actor actor:enemyGrid.getChildren()){
            actor.setVisible(true);
        }
    }
    
  

}
