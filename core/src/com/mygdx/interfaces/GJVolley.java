package com.mygdx.interfaces;

import java.util.List;

import com.mygdx.data.EnemyData;
import com.mygdx.data.UnitData;
import com.mygdx.screen.NetworkLoadingScreen;

public abstract class GJVolley {
    public static final String url = "http://grandknights.hol.es/grand_knights/battle/index";
    protected List<UnitData> unitDAO;
    protected List<EnemyData> enemyDAO;
    
    //needs to be implemented on android project
    public abstract void createDAO(String url,  NetworkLoadingScreen loadScreen);
    
    public List<UnitData> getUnits(){
        return unitDAO;
    }
    
    public List<EnemyData> getEnemy(){
        return enemyDAO;
    }
}
