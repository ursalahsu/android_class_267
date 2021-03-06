package com.example.user.simpleui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnDrinkOrderListener {

    List<Drink> drinks = new ArrayList<>();
    ArrayList<DrinkOrder> drinkOrders = new ArrayList<>();
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
                Drink drink = (Drink)parent.getAdapter().getItem(position);
                ShowDetailDrinkMenu(drink);
            }
        });
    }
    private void ShowDetailDrinkMenu(Drink drink){
        //getSupportFragmentManager() 使用在舊版的android 要改Fragment java檔中的import
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        DrinkOrder drinkOrder = new DrinkOrder();
        Boolean flag=false; //訂單是否存在
        for(DrinkOrder order : drinkOrders){
            if(order.drinkName.equals(drink.getName())){
                drinkOrder=order;
                flag=true;
                break;
            }
        }
        if(!flag){
            drinkOrder.mPrice=drink.getMPrice();
            drinkOrder.lPrice=drink.getLPrice();
            drinkOrder.drinkName=drink.getName();
        }


        DrinkOrderDialog orderDialog = DrinkOrderDialog.newInstance(drinkOrder);
        //DialogFragment的實作方式
        orderDialog.show(ft,"DrinkOrderDialog");
        //Fragment實作方式
//        ft.replace(R.id.root,orderDialog);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    private void setData() {
        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(final List<Drink> objects, ParseException e) {
//                if(e==null){
//                    drinks=objects;
//                    setupListView();
//                }
                if( e != null){
                    Drink.getQuery().fromLocalDatastore().findInBackground(new FindCallback<Drink>() {
                        @Override
                        public void done(List<Drink> list, ParseException e) {
                            drinks = list;
                            setupListView();
                        }
                    });
                }else{
                    //刪除Local端資料
                    ParseObject.unpinAllInBackground("Drink", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(objects, new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    drinks=objects;
                                    setupListView();
                                }
                            });
                        }
                    });
                }
            }
        });
//        for (int i = 0; i < 4; i++) {
//            Drink drink = new Drink();
//            drink.name = names[i];
//            drink.mPrice = mPrices[i];
//            drink.lPrice = lPrices[i];
//            drink.imageId = imageId[i];
//            drinks.add(drink);
//        }
    }

    public void setupListView() {
        DrinkAdapter adapter = new DrinkAdapter(this,drinks);
        drinkListView.setAdapter(adapter);
    }

    public void updateTotalPrice() {
        int total = 0;
        for(DrinkOrder order :drinkOrders){
            total +=order.mPrice*order.mNumber+order.lPrice*order.lNumber;
        }
        priceTextView.setText(String.valueOf(total));
    }
    //OK
    public void done(View view){
        Intent intent=new Intent(); //只能傳遞基本型態
        //經由JSON定義回傳字串
        JSONArray array = new JSONArray();
        for(DrinkOrder order : drinkOrders){
            JSONObject object = order.getJsonObject();
            array.put(object);
        }
        //第一個參數為鍵值 第二個為所要存放的資料內容
        intent.putExtra("results",array.toString());
        //回傳執行成功並回傳互相溝通的意圖
        setResult(RESULT_OK,intent);
        //結束Activity
        finish();
    }
    //CANCEL
    public void cancel(View view){
        //回傳取消
        setResult(RESULT_CANCELED);
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

    @Override
    public void OnDrinkOrderFinished(DrinkOrder drinkOrder) {
        for(int i=0 ;i<drinkOrders.size();i++){
            if(drinkOrders.get(i).drinkName.equals(drinkOrder.drinkName)){
                drinkOrders.set(i,drinkOrder);
                updateTotalPrice();
                return;
            }
        }
        drinkOrders.add(drinkOrder);
        updateTotalPrice();
    }
}
