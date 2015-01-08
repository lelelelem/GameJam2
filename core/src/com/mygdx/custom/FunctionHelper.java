package com.mygdx.custom;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.screen.PreBattleScreen;


public class FunctionHelper {

    private static final int ENEMY = 1;
    private static final int UNIT = 0;
    
    private FunctionHelper(){
        
    }
    
    //move to TargetGrid
    //loop to all UnitGrids
    public static void computeTargetRange(int mode, GJUnitGrid unitGrid, PreBattleScreen preBattle){
        String gridCoordinates[] = unitGrid.getName().split("-");
        int gridX = Integer.parseInt(gridCoordinates[0]);
        int gridY = Integer.parseInt(gridCoordinates[1]);
         
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
    
    public static void doDamage(GJUnitGrid unitGrid, TargetGrid targets){
        boolean hasTarget = false;
        
    	 String gridCoordinates[] = unitGrid.getName().split("-");
    	 int gridX = Integer.parseInt(gridCoordinates[0]);
         int gridY = Integer.parseInt(gridCoordinates[1]);
    
         for (String spec: unitGrid.getRange()){
        	 String ranges[] = spec.split("_");
             int xMod = Integer.parseInt(ranges[0]);
             int yMod = Integer.parseInt(ranges[1]);
             String coor = Integer.toString(gridX + xMod) +"-"+ (Integer.toString(gridY+yMod));
             GJUnitGrid grid = targets.findActor(coor);
           
             if (grid!=null){
                 GJAnimatingActor toAnimate = null;
                 if (grid.getUnit()!=null){
                     hasTarget = true;
                     toAnimate = grid.getUnit();
                     
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         hasTarget = false;
                         continue;
                     }
                     createDamage(UNIT, toAnimate, grid, unitGrid);
                 }
                 else if (grid.getEnemy()!=null){
                     hasTarget = true;
                     
                     toAnimate = grid.getEnemy();
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         hasTarget = false;
                         continue;
                     }
                     createDamage(ENEMY, toAnimate, grid, unitGrid);
                 
                 }
            	 
            	 if (toAnimate!=null){
            	     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
                         grid.startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
            	         toAnimate.playDeadAnimation();
            	     }
            	     else{
            	         toAnimate.playHurtAnimation();
            	         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
            	         grid.startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
            	     }
            	 }
             }
         }
         
         if (!hasTarget){
             for (Actor target:targets.getChildren()){
                 
                 if (((GJUnitGrid)target).getUnit()!=null){
                     GJAnimatingActor toAnimate = ((GJUnitGrid)target).getUnit();
                     
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         continue;
                     }
                     
                     createDamageForHalf(UNIT, toAnimate,  ((GJUnitGrid)target), unitGrid);
                     
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
                         ((GJUnitGrid)target).startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
                         toAnimate.playDeadAnimation();
                     }
                     else{
                         toAnimate.playHurtAnimation();
                         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
                         ((GJUnitGrid)target).startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
                     }
                     
                     break;
                 }
                 
                 if (((GJUnitGrid)target).getEnemy()!=null){
                     GJAnimatingActor toAnimate = ((GJUnitGrid)target).getEnemy();
                     
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         continue;
                     }
                     
                     createDamageForHalf(ENEMY, toAnimate,  ((GJUnitGrid)target), unitGrid);
                     
                     if (toAnimate.getStatus()==GJAnimatingActor.ISDEAD){
                         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
                         ((GJUnitGrid)target).startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
                         toAnimate.playDeadAnimation();
                     }
                     else{
                         toAnimate.playHurtAnimation();
                         GJAnimatingActor actorAnimate = unitGrid.getEnemy()!=null?unitGrid.getEnemy(): unitGrid.getUnit();
                         ((GJUnitGrid)target).startPlayAnimation(actorAnimate.getAttackEffectAnimation());    
                     }
                     
                     break;
                 }
             }
         }
         
    }
    
    private static String computeRealDmg(float attack_attacker, float def_target){
        return  Integer.toString((int)(attack_attacker*((4000+def_target)/(4000+(def_target*10)))));
    }
    private static int computeDmg(float attack_attacker, float def_target, float hp_target){
        return (int)(hp_target - (attack_attacker*((4000+def_target)/(4000+(def_target*10)))));
    }
    
    private static void createDamage(int mode, GJAnimatingActor toAnimate, GJUnitGrid grid, GJUnitGrid unitGrid){
        
        if (mode==UNIT){
            grid.getUnit().getUnitData().setHp(computeDmg(unitGrid.getEnemy().getEnemyData().getAtk(), grid.getUnit().getUnitData().getDef(), grid.getUnit().getUnitData().getHp()));
            grid.showDamage(computeRealDmg(unitGrid.getEnemy().getEnemyData().getAtk(), grid.getUnit().getUnitData().getDef()));
           
            if (grid.getUnit().getUnitData().getHp()<=0){
                toAnimate.setStatus(GJAnimatingActor.ISDEAD);
            }
        }
        
        else if (mode ==ENEMY){
            grid.getEnemy().getEnemyData().setHp(computeDmg(unitGrid.getUnit().getUnitData().getAtk(),  grid.getEnemy().getEnemyData().getDef(), grid.getEnemy().getEnemyData().getHp()));
            grid.showDamage(computeRealDmg(unitGrid.getUnit().getUnitData().getAtk(),  grid.getEnemy().getEnemyData().getDef()));
            if (grid.getEnemy().getEnemyData().getHp()<=0){
                 
                toAnimate.setStatus(GJAnimatingActor.ISDEAD);
            }
        }
       
    }
    
    private static void createDamageForHalf(int mode, GJAnimatingActor toAnimate, GJUnitGrid grid, GJUnitGrid unitGrid){
        if (mode==UNIT){
            grid.getUnit().getUnitData().setHp(computeDmg(unitGrid.getEnemy().getEnemyData().getAtk()/2, grid.getUnit().getUnitData().getDef(), grid.getUnit().getUnitData().getHp()));
            grid.showDamage(computeRealDmg(unitGrid.getEnemy().getEnemyData().getAtk()/2, grid.getUnit().getUnitData().getDef()));
           
            if (grid.getUnit().getUnitData().getHp()<=0){
                toAnimate.setStatus(GJAnimatingActor.ISDEAD);
            }
        }
        
        else if (mode ==ENEMY){
            grid.getEnemy().getEnemyData().setHp(computeDmg(unitGrid.getUnit().getUnitData().getAtk()/2,  grid.getEnemy().getEnemyData().getDef(), grid.getEnemy().getEnemyData().getHp()));
            grid.showDamage(computeRealDmg(unitGrid.getUnit().getUnitData().getAtk()/2,  grid.getEnemy().getEnemyData().getDef()));
            if (grid.getEnemy().getEnemyData().getHp()<=0){
                 
                toAnimate.setStatus(GJAnimatingActor.ISDEAD);
            }
        }
    }
    
}
