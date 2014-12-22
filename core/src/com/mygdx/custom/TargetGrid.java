package com.mygdx.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.screen.PreBattleScreen;

public class TargetGrid extends Group {
    
    private GJUnitGrid targetGrid00;
    private GJUnitGrid targetGrid01;
    private GJUnitGrid targetGrid02;
    private GJUnitGrid targetGrid10;
    private GJUnitGrid targetGrid11;
    private GJUnitGrid targetGrid12;
    private GJUnitGrid targetGrid20;
    private GJUnitGrid targetGrid21;
    private GJUnitGrid targetGrid22;
    
    private PreBattleScreen preBattle;
    
    public TargetGrid(PreBattleScreen preBattle){
        this.preBattle = preBattle;
        targetGrid00 = new GJUnitGrid("0-0", preBattle);
        targetGrid01 = new GJUnitGrid("1-0", preBattle);
        targetGrid02 = new GJUnitGrid("2-0", preBattle);
        targetGrid10 = new GJUnitGrid("0-1", preBattle);
        targetGrid11 = new GJUnitGrid("1-1", preBattle);
        targetGrid12 = new GJUnitGrid("2-1", preBattle);
        targetGrid20 = new GJUnitGrid("0-2", preBattle);
        targetGrid21 = new GJUnitGrid("1-2", preBattle);
        targetGrid22 = new GJUnitGrid("2-2", preBattle);
        
        this.addActor(targetGrid00);
        this.addActor(targetGrid01);
        this.addActor(targetGrid02);
        this.addActor(targetGrid10);
        this.addActor(targetGrid11);
        this.addActor(targetGrid12);
        this.addActor(targetGrid20);
        this.addActor(targetGrid21);
        this.addActor(targetGrid22);
        
        this.setWidth(targetGrid00.getWidth()*3);
        this.setHeight(targetGrid00.getHeight()*3);
        
        float row=1;
        float col = 1;
        
        for (Actor actor:this.getChildren()){
            actor.setX(this.getWidth()-(col*actor.getWidth()));
            actor.setY(this.getHeight()-(row*actor.getHeight()));
            if (col==3){
                col =1;
                row++;
            }
            else{
                col++;    
            }
            
        }   
    }
    
    public void highLightGrid(String name){
        for (Actor grid:this.getChildren()){
            if (grid.getName().equals(name)){
                ((GJUnitGrid)grid).untoggle();
            }
        }
    }
    
    public void clearGrid(String unitName, String toSkip){
        for (Actor grid:this.getChildren()){
            if(((GJUnitGrid)grid).getUnit()!=null){
                if(((GJUnitGrid)grid).getUnit().getUnitData().getUnit_name().equals(unitName)){
                    Gdx.app.log("lem", "clearing");
                    ((GJUnitGrid)grid).clearUnit();
                    FunctionHelper.computeTargetRange(GJUnitGrid.UNTOGGLE, ((GJUnitGrid)grid), preBattle);
                }   
            }
        }
        refreshGrid(GJUnitGrid.TOGGLE);
    }
    
    
    //enemygrid is the one being used
    public void refreshGrid(int mode){
        for (Actor grid:preBattle.getEnemyGrid().getChildren()){
            ((GJUnitGrid)grid).untoggle();
        }
        
        for(Actor grid:preBattle.getUnitGrid().getChildren()){
            GJUnitGrid temp = ((GJUnitGrid)grid);
            
            if (temp.getUnit() == null){
                continue;
            }
            
            FunctionHelper.computeTargetRange(mode, temp, preBattle);
        }
    }
    
    public void addToSpecificGrid(String gridName, GJEnemy enemy){
       Group tempG = (Group)this.findActor(gridName);
        ((GJUnitGrid)tempG).addEnemy(enemy);
    }
    
   
}
