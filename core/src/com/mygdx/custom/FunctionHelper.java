package com.mygdx.custom;
import com.badlogic.gdx.Gdx;
import com.mygdx.screen.PreBattleScreen;


public class FunctionHelper {

    private FunctionHelper(){
        
    }
    
    //move to TargetGrid
    //loop to all UnitGrids
    public static void computeTargetRange(int mode, GJUnitGrid unitGrid, PreBattleScreen preBattle){
        Gdx.app.log("lem", "starting compute");
        String gridCoordinates[] = unitGrid.getName().split("-");
        int gridX = Integer.parseInt(gridCoordinates[0]);
        int gridY = Integer.parseInt(gridCoordinates[1]);
        Gdx.app.log("lem", "gridx "+ gridX+" gridy "+gridY);
        for (String spec: unitGrid.getRange()){
            String ranges[] = spec.split("_");
            int xMod = Integer.parseInt(ranges[0]);
            int yMod = Integer.parseInt(ranges[1]);
            Gdx.app.log("lem", "x "+ xMod+" y "+yMod);
            String coor = Integer.toString(gridX + xMod) +"-"+ (Integer.toString(gridY+yMod));
            GJUnitGrid grid = preBattle.getEnemyGrid().findActor(coor);
            if (grid!=null){
                if (mode == GJUnitGrid.UNTOGGLE){
                    preBattle.getEnemyGrid().highLightGrid(coor);
                }   
                else{
                    Gdx.app.log("lem", "found "+coor);
                    grid.toggle();
                }
            }
        }
    }   
    
    
}
