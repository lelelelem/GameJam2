package com.mygdx.data;

import jdk.nashorn.internal.parser.JSONParser;

public class UnitData {
    
    private int atk;
    private int def;
    private int hp;
    
    private int unit_id;
    private int class_id;
    private int burst;
    private int level;
    private int agi;
    private int str;
    private int vit;
    private int inte;
    
    private String atk_range;
    private String unit_name;
    private String display_name;
    private String target_range;
    
    public void setUnit_id(int unit_id){
        this.unit_id = unit_id;
    }
    
    public int getUnit_id() {
        return unit_id;
    }
    
    public int getClass_id() {
        return class_id;
    }
   
    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
 
    public int getAgi() {
        return agi;
    }
  
    public void setAgi(int agi) {
        this.agi = agi;
    }
 
    public int getStr() {
        return str;
    }
   
    public void setStr(int str) {
        this.str = str;
    }
  
    public int getVit() {
        return vit;
    }
 
    public void setVit(int hp) {
        this.vit = hp;
    }
   
    public String getAtk_range() {
        return atk_range;
    }
   
    public void setAtk_range(String atk_range) {
        this.atk_range = atk_range;
    }

  public String getUnit_name() {
        return unit_name;
    }
   
  public String getDisplay_name() {
        return display_name;
    }
 
  public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
  
  
  public void setUnit_name(String unit_name) {
      this.unit_name = unit_name;
  }

public int getInte() {
    return inte;
}

public void setInte(int inte) {
    this.inte = inte;
}

public int getBurst() {
    return burst;
}

public void setBurst(int burst) {
    this.burst = burst;
}

public int getLevel() {
    return level;
}

public void setLevel(int level) {
    this.level = level;
}

public String getTarget_range() {
    return target_range;
}

public void setTarget_range(String target_range) {
    this.target_range = target_range;
}

public int getAtk() {
    return atk;
}

public void setAtk(int atk) {
    this.atk = atk;
}

public int getDef() {
    return def;
}

public void setDef(int def) {
    this.def = def;
}

public int getHp() {
    return hp;
}

public void setHp(int hp) {
    this.hp = hp;
}




}
