package com.example.user.simpleui;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/6/13.
 */
@ParseClassName("Drink")
public class Drink extends ParseObject{
//    String name;
//    int mPrice;
//    int lPrice;
//    int imageId;
    public String getName(){return getString("name");}
    public void setName(String name){put("name",name);}

    public int getMPrice(){return getInt("mPrice");}
    public void setMPrice(int mPrice){put("mPrice",mPrice);}

    public int getLPrice(){return getInt("lPrice");}
    public void setLPrice(int lPrice){put("lPrice",lPrice);}

    public void setImage(ParseFile file){put("image",file);}
    public ParseFile getImage(){return getParseFile("image");}

    public static ParseQuery<Drink> getQuery(){return ParseQuery.getQuery(Drink.class);}

    public JSONObject getData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", getName());
            jsonObject.put("price", getMPrice());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
//JSON內容
//[
//        {
//            name:"珍珠奶茶"
//            price:35
//        }
//        {
//            name:"多多綠茶"
//            price:30
//        }
//]
    }
}
