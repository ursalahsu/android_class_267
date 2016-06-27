package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class OrderDetailActivity extends AppCompatActivity {

    TextView noteTextView;
    TextView menuResultsTextView;
    TextView storeInfoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent=getIntent();
        String note =intent.getStringExtra("note");
        String menuResults=intent.getStringExtra("menuResults");
        String storeInfo=intent.getStringExtra("storeInfo");

//        Log.d("debug",note);
//        Log.d("debug",menuResults);
//        Log.d("debug",storeInfo);
        noteTextView = (TextView)findViewById(R.id.noteTextView);
        menuResultsTextView = (TextView)findViewById(R.id.menuResultTextView);
        storeInfoTextView = (TextView)findViewById(R.id.storeInfoTextView);

        if(note !=null)
            noteTextView.setText(note);

        if(storeInfo!=null)
            storeInfoTextView.setText(storeInfo);

        String text="";
        if(menuResults!=null){
            try {
                JSONArray jsonArray=new JSONArray(menuResults);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String lNumber=String.valueOf(object.getInt("lNumber"));
                    String mNumber=String.valueOf(object.getInt("mNumber"));
                    text += object.getString("drinkName") + ":大杯" + lNumber + "杯   中杯" + mNumber +"杯\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            menuResultsTextView.setText(text);
        }
    }
}
