package com.mygdx.interfaces;

import java.util.List;

import com.mygdx.data.EnemyData;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.screen.NetworkLoadingScreen;

public abstract class GJVolley {
    public static final String url = "http://grandknights.hol.es/grand_knights/battle/index?world_id=1&world_seq=1&stage_part=1";
    protected List<UnitData> unitDAO;
    protected List<StageData> stages;
    protected List<EnemyData> enemyDAO;
    
    //needs to be implemented on android project
    public abstract void createDAO(String url,  NetworkLoadingScreen loadScreen, VolleyResultListener volleyResultListener);
    
    public List<UnitData> getUnits(){
        return unitDAO;
    }
    
    public List<StageData> getStages(){
        return stages;
    }
}
