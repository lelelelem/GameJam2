package com.mygdx.custom;
import com.badlogic.gdx.Gdx;
import com.mygdx.screen.PreBattleScreen;


public class FunctionHelper {

    private FunctionHelper(){
        
    }
    
    //move to TargetGrid
    //loop to all UnitGrids
    public static void computeTargetRange(int mode, GJUnitGrid unitGrid, PreBattleScreen preBattle){
        String gridCoordinates[] = unitGrid.getName().split("-");
        int gridX = Integer.parseInt(gridCoordinates[0]);
        int gridY = Integer.parseInt(gridCoordinates[1]);
        Gdx.app.log("lem", "gridx "+ gridX+" gridy "+gridY);
        for (String spec: unitGrid.getRange()){
            String ranges[] = spec.split("_");
            int xMod = Integer.parseInt(ranges[0]);
            int yMod = Integer.parseInt(ranges[1]);
            
            String coor = Integer.toString(gridX + xMod) +"-"+ (Integer.toString(gridY+yMod));
            GJUnitGrid grid = preBattle.getEnemyGrid().findActor(coor);
            if (grid!=null){
                if (mode == GJUnitGrid.UNTOGGLE){
                    preBattle.getEnemyGrid().highLightGrid(coor);
                }   
                else{
                    grid.toggle();
                }
            }
        }
    }
    
    public static void doDamage(int mode, GJUnitGrid unitGrid, TargetGrid targets){
    	 String gridCoordinates[] = unitGrid.getName().split("-");
    	 int gridX = Integer.parseInt(gridCoordinates[0]);
         int gridY = Integer.parseInt(gridCoordinates[1]);
         Gdx.app.log("lem", "gridx "+ gridX+" gridy "+gridY);
         for (String spec: unitGrid.getRange()){
        	 String ranges[] = spec.split("_");
             int xMod = Integer.parseInt(ranges[0]);
             int yMod = Integer.parseInt(ranges[1]);
             String coor = Integer.toString(gridX + xMod) +"-"+ (Integer.toString(gridY+yMod));
             GJUnitGrid grid = targets.findActor(coor);
             if (grid!=null){
            	 GJAnimatingActor toAnimate= grid.getUnit() !=null? grid.getUnit():grid.getEnemy();
            	 if (toAnimate!=null){
            		 toAnimate.playHurtAnimation();	
            		 
            	 }
             }
         }
    }
    
    
}
