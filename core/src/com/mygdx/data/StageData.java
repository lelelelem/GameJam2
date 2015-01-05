package com.mygdx.data;

import java.util.ArrayList;
import java.util.List;

public class StageData {
    
    private List<EnemyData> enemies = new ArrayList<EnemyData>();
    private int stage_id;
    
    public List<EnemyData> getEnemies() {
        return enemies;
    }
    public void addEnemies(EnemyData enemiyData) {
        enemies.add(enemiyData);
    }
    public int getStage_id() {
        return stage_id;
    }
    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
    }
    
    

}
