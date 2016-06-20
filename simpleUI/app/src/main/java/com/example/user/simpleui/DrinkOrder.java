package com.example.user.simpleui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/6/16.
 */
public class DrinkOrder {
    String drinkName;
    String ice = "正常";
    String sugar = "正常";
    String note="";
    int lNumber = 0;
    int mNumber = 1;
    int lPrice;
    int mPrice;
    //轉成JSON
    public JSONObject getJsonObject(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("drinkName", drinkName);
            jsonObject.put("ice",ice);
            jsonObject.put("sugar",sugar);
            jsonObject.put("note",note);
            jsonObject.put("lNumber",lNumber);
            jsonObject.put("mNumber",mNumber);
            jsonObject.put("lPrice",lPrice);
            jsonObject.put("mPrice",mPrice);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    //復原資料結構
    public static DrinkOrder newInstanceWithJsonObject(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            DrinkOrder drinkOrder = new DrinkOrder();
            drinkOrder.drinkName=jsonObject.getString("drinkName");
            drinkOrder.ice=jsonObject.getString("ice");
            drinkOrder.sugar=jsonObject.getString("sugar");
            drinkOrder.note=jsonObject.getString("note");
            drinkOrder.lNumber=jsonObject.getInt("lNumber");
            drinkOrder.mNumber=jsonObject.getInt("mNumber");
            drinkOrder.lPrice=jsonObject.getInt("lPrice");
            drinkOrder.mPrice=jsonObject.getInt("mPrice");
            return drinkOrder;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
