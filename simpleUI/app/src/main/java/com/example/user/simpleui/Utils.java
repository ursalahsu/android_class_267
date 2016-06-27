package com.example.user.simpleui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by user on 2016/6/20.
 */
public class Utils {
    public static void writeFile(Context context,String fileName,String content) {
        try {
            FileOutputStream fos=context.openFileOutput(fileName,Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readFile(Context context,String fileName){
        try {
            FileInputStream fis=context.openFileInput(fileName);
            byte[] buffer =new byte[1024];
            fis.read(buffer, 0, buffer.length);
            fis.close();
            String string = new String(buffer);
            return string;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //取網頁資料
    public static byte[] urlToBytes(String urlString){
        try {
            URL url=new URL(urlString);
            URLConnection connection= url.openConnection();
            InputStream inputStream=connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer= new byte[1024];
            int len=0;
            while ((len=inputStream.read(buffer)) !=-1){
                byteArrayOutputStream.write(buffer,0,len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //取得google map JSON 並解譯
    public static double[] getLatLngFromGoogleMapAPI(String address){
        try {
            //轉碼
            address= URLEncoder.encode(address,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String apiURL="http://maps.google.com/maps/api/geocode/json?address="+address;
        byte[] bytes=urlToBytes(apiURL);

        if(bytes==null)
            return null;

        String result = new String(bytes);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("OK")){
                JSONObject location = jsonObject.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");
                double lat=location.getDouble("lat");
                double lng=location.getDouble("lng");

                return new double[]{lat,lng};

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getStaticMap(double[] latlng){
        String center = String.valueOf(latlng[0]+","+String.valueOf(latlng[1]));
        String staticMapUrl = "http://maps.google.com/maps/api/staticmap?center="+center+"&size=640x400&zoom=17";

        byte[] bytes = Utils.urlToBytes(staticMapUrl);
        if(bytes!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            return bitmap;
        }
        return null;
    }
}
