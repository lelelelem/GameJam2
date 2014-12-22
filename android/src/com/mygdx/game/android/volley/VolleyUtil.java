package com.mygdx.game.android.volley;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.badlogic.gdx.Gdx;
import com.mygdx.data.EnemyData;
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
        
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    unitDAO = setupDAO(response);
                    enemyDAO = setupEnemyDAO(response);
                    loadScreen.hasLoaded();
                } catch (JSONException e) {
                    Gdx.app.log("lem", "fail");
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
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
        Gdx.app.log("data", "lem "+array.toString());
        
        for (int i =0; i<array.length(); i++){
            UnitData unitData = new UnitData();
            JSONObject obj = (JSONObject) array.get(i);
            
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
    
    private synchronized List<EnemyData> setupEnemyDAO(JSONObject jsonObj) throws JSONException{
        List<EnemyData> unitList  = new ArrayList<EnemyData>();
        
        JSONArray array = jsonObj.getJSONArray("enemy_units");
        Log.i("lem","fetching data");
        Gdx.app.log("data", "lem "+array.toString());
        
        for (int i =0; i<array.length(); i++){
            EnemyData unitData = new EnemyData();
            JSONObject obj = (JSONObject) array.get(i);
            unitData.setCoordinates(obj.getString("coordinates"));
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
}
