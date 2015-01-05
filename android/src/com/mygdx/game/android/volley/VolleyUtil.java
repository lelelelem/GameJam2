package com.mygdx.game.android.volley;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.badlogic.gdx.Gdx;
import com.mygdx.data.EnemyData;
import com.mygdx.data.StageData;
import com.mygdx.data.UnitData;
import com.mygdx.interfaces.GJVolley;
import com.mygdx.screen.NetworkLoadingScreen;

public class VolleyUtil extends GJVolley {
    
    public static final int GET_UNIT = 0;
    public static final int GET_ENEMY = 1;
    
    private Context context;
    private RequestQueue requestQueue;
    private static VolleyUtil volleyUtil;

    private VolleyUtil(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    
    public static synchronized VolleyUtil getInstance(){
        return volleyUtil;
    }
    
    public static synchronized void init(Context context){
        volleyUtil = new VolleyUtil(context);
        
    }
    
    public void createDAO(String url, final NetworkLoadingScreen loadScreen){
        Log.i("lem", "connecting to "+url);
        
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.PUBLIC, url, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    unitDAO = setupDAO(response);
                    stages = obtainStages(response);
                    loadScreen.hasLoaded();
                } catch (JSONException e) {
                     
                    e.printStackTrace();
                }
            }

        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("lem", "error " + error.getMessage());
            }
        });
        requestQueue.add(jsonObjReq);
    }
    
    private synchronized List<UnitData> setupDAO(JSONObject jsonObj) throws JSONException{
        List<UnitData> unitList  = new ArrayList<UnitData>();
        
        JSONArray array = jsonObj.getJSONArray("units");
        Log.i("lem","fetching data");
         
        
        for (int i =0; i<array.length(); i++){
            UnitData unitData = new UnitData();
            JSONObject obj = (JSONObject) array.get(i);
            
            unitData.setAtk(obj.getInt("atk"));
            unitData.setDef(obj.getInt("def"));
            unitData.setHp(obj.getInt("hp"));
            unitData.setUnit_id(obj.getInt("id"));        
            unitData.setUnit_name(obj.getString("name"));
            unitData.setDisplay_name(obj.getString("display_name"));
            unitData.setClass_id(obj.getInt("class_id"));
            unitData.setInte(obj.getInt("int"));
            unitData.setLevel(obj.getInt("current_lvl"));
            unitData.setAgi(obj.getInt("agi"));
            unitData.setStr(obj.getInt("str"));
            unitData.setVit(obj.getInt("vit"));
            unitData.setBurst(obj.getInt("burst"));
            unitData.setTarget_range(obj.getString("target_range"));
            unitList.add(unitData);
        }
        
        return unitList;
    }
    
    private synchronized List<StageData> obtainStages(JSONObject jsonObj) throws JSONException{
        List<StageData> stageList  = new ArrayList<StageData>();
        
        JSONObject world_info = jsonObj.getJSONObject("world_info");
        JSONArray array = world_info.getJSONArray("stages");
        Log.i("lem","fetching data");
         
        for (int i =0; i<array.length(); i++){
            StageData stage = new StageData();
            JSONObject obj = (JSONObject) array.get(i);
            
            stage.setStage_id(obj.getInt("stage_part"));
            JSONArray enemyData = obj.getJSONArray("enemy_units");
            
            for (int j=0; j<enemyData.length();j++){
                EnemyData unitData = new EnemyData();
                
                unitData.setAtk(enemyData.getJSONObject(j).getInt("atk"));
                unitData.setDef(enemyData.getJSONObject(j).getInt("def"));
                unitData.setHp(enemyData.getJSONObject(j).getInt("hp"));
                unitData.setCoordinates(enemyData.getJSONObject(j).getString("coordinates"));
                unitData.setUnit_id(enemyData.getJSONObject(j).getInt("id"));        
                unitData.setUnit_name(enemyData.getJSONObject(j).getString("name"));
                unitData.setDisplay_name(enemyData.getJSONObject(j).getString("display_name"));
                unitData.setClass_id(enemyData.getJSONObject(j).getInt("class_id"));
                unitData.setInte(enemyData.getJSONObject(j).getInt("int"));
                unitData.setLevel(enemyData.getJSONObject(j).getInt("current_lvl"));
                unitData.setAgi(enemyData.getJSONObject(j).getInt("agi"));
                unitData.setStr(enemyData.getJSONObject(j).getInt("str"));
                unitData.setVit(enemyData.getJSONObject(j).getInt("vit"));
                unitData.setBurst(enemyData.getJSONObject(j).getInt("burst"));
                unitData.setTarget_range(enemyData.getJSONObject(j).getString("target_range"));
                
                stage.addEnemies(unitData);
            }
            stageList.add(stage);
        }
        
        return stageList;
    }
}
