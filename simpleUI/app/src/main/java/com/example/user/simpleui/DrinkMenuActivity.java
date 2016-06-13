package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DrinkMenuActivity extends AppCompatActivity {

    ArrayList<Drink> drinks = new ArrayList<>();
    ArrayList<Drink> drinkOrders = new ArrayList<>();
    ListView drinkListView;
    TextView priceTextView;

    //set data
    String[] names = {"珍珠奶茶", "多多綠茶", "玫瑰鹽奶蓋紅茶", "紅茶拿鐵"};
    int[] mPrices = {35, 30, 55, 45};
    int[] lPrices = {45, 40, 65, 55};
    int[] imageId = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
        Log.d("Debug", "Drink Menu Activity OnCreate");

        setData();
        drinkListView = (ListView) findViewById(R.id.drinkListView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);

        setupListView();

        drinkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrinkAdapter drinkAdapter = (DrinkAdapter)parent.getAdapter();
                Drink drink = (Drink)drinkAdapter.getItem(position);
                drinkOrders.add(drink);
                updateTotalPrice();
            }
        });
    }

    private void setData() {
        for (int i = 0; i < 4; i++) {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.mPrice = mPrices[i];
            drink.lPrice = lPrices[i];
            drink.imageId = imageId[i];
            drinks.add(drink);
        }
    }

    public void setupListView() {
        DrinkAdapter adapter = new DrinkAdapter(this,drinks);
        drinkListView.setAdapter(adapter);
    }

    public void updateTotalPrice() {
        int total = 0;
        for(Drink drink :drinkOrders){
            total +=drink.mPrice;
        }
        priceTextView.setText(String.valueOf(total));
    }

    public void done(View view){
        Intent intent=new Intent(); //只能傳遞基本型態
        //經由JSON定義回傳字串
        JSONArray array = new JSONArray();
        for(Drink drink : drinkOrders){
            JSONObject object = drink.getDate();
            array.put(object);
        }
        //第一個參數為鍵值 第二個為所要存放的資料內容
        intent.putExtra("results",array.toString());
        //回船執行成功並回傳互相溝通的意圖
        setResult(RESULT_OK,intent);
        //結束Activity
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Debug", "Drink Menu Activity OnStrat");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "Drink Menu Activity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug", "Drink Menu Activity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Debug", "Drink Menu Activity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Debug", "Drink Menu Activity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug", "Drink Menu Activity OnRestart");
    }
}
