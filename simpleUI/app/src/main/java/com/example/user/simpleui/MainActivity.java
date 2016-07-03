package com.example.user.simpleui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    CheckBox checkBox;
    ListView listView;
    Spinner storeSpinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;    //需要使用editor寫入至SharedPreferences

    List<Order> orders = new ArrayList<>();
    String drinkName = "black tea";
    String menuResults ="";

    static int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;
    List<String> spinnerData = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Parse TEST
        //new 一個class 名稱叫TestObject
        ParseObject testObject = new ParseObject("TestObject");
        //             key    value
        testObject.put("foo", "bar");
        //背景上傳
        testObject.saveInBackground();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject object : objects){
                        Toast.makeText(MainActivity.this,object.getString("foo"),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        //背景執行 需有Callback回傳狀況
//        query.findInBackground(new FindCallback() {
//            @Override
//            public void done(List objects, ParseException e) {
//                //處理連線問題
//                if(e==null){
//                    for(int i=0;i<objects.size();i++){
//                        Log.d("debug",objects.get(i).toString());
//                    }
//                }
//            }
//
//            @Override
//            public void done(Object o, Throwable throwable) {
//
//            }
//        });

        setContentView(R.layout.activity_main);
        Log.d("Debug", "Hello LOG");

        //使用Android提供的工具做資料儲存
        sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE); //使用覆蓋
        editor = sharedPreferences.edit();
        setupOrdersData();

        textView = (TextView) findViewById(R.id.textview);
        textView.setText("Hello World");

        editText = (EditText) findViewById(R.id.editText);
        editText.setText(sharedPreferences.getString("editText", ""));
        //監聽輸入
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = editText.getText().toString();
                editor.putString("editText", text);
                editor.apply(); //寫入至SharedPreferences

                //偵測enter按鈕 動作是按下按鈕
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    click(v);
                    return true; //把enter攔截 避免顯示在editView中
                }
                return false;
            }
        });

        textView.setText(sharedPreferences.getString("textView", ""));
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = textView.getText().toString();
                editor.putString("textView", text);
                editor.apply(); //寫入至SharedPreferences
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        checkBox = (CheckBox) findViewById(R.id.checkBox);

        listView = (ListView) findViewById(R.id.listView);
        setupListView();

        storeSpinner= (Spinner) findViewById(R.id.spinner);
        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int select = storeSpinner.getSelectedItemPosition();
                editor.putInt("Spinner",select);
                editor.apply(); //寫入至SharedPreferences
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setupSpinner();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order)parent.getAdapter().getItem(position);
                goToDetailOrder(order);
            }
        });

        Log.d("Debug", "Main Activity OnCreate");



    }

    private void goToDetailOrder(Order order) {
        Intent intent = new Intent();
        intent.setClass(this,OrderDetailActivity.class);

        intent.putExtra("note",order.getNote());
        intent.putExtra("menuResults",order.getMenuResults());
        intent.putExtra("storeInfo",order.getStoreInfo());

        startActivity(intent);
    }

    void setupSpinner(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StoreInfo");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject object : objects){
                        Toast.makeText(MainActivity.this,object.getString("name")+","+object.getString("address"),Toast.LENGTH_SHORT).show();
                        spinnerData.add(object.getString("name")+","+object.getString("address"));
                    }
                }
            }
        });
        String[] data = spinnerData.toArray(new String[spinnerData.size()]);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,data);
        storeSpinner.setAdapter(adapter);

//        //讀取Resouce內的String Array XML檔
//        String[] data = getResources().getStringArray(R.array.storeInfo);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
//        storeSpinner.setAdapter(adapter);
//        storeSpinner.setSelection(sharedPreferences.getInt("Spinner", 0));
    }

    void setupListView() {
        OrderAdapter adapter = new OrderAdapter(this,orders);
        listView.setAdapter(adapter);
    }

    private void setupOrdersData(){
        //檢查網路狀態
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info =cm.getActiveNetworkInfo();

        final FindCallback<Order> callback = new FindCallback<Order>() {
            @Override
            public void done(List<Order> objects, ParseException e) {
                //要判斷沒有錯誤才執行 不然程式會崩潰
                if(e==null) {
                    orders = objects;
                    setupListView();
                }
            }
        };
        //有連線成功 從雲端取資料; 無則使用LocalDatabase
        if(info!=null && info.isConnected()){
            Order.getOrdersFromRemote(new FindCallback<Order>() {
                @Override
                public void done(List<Order> objects, ParseException e) {
                    //有網路但是取資料發生問題 還是取Local端資料
                    if(e!=null){
                        Toast.makeText(MainActivity.this,"Sync Failed",Toast.LENGTH_LONG).show();
                        Order.getQuery().fromLocalDatastore().findInBackground(callback);
                    }else{
                        callback.done(objects,e);
                    }

                }
            });
        }else{
            Order.getQuery().fromLocalDatastore().findInBackground(callback);
        }

//        String content = Utils.readFile(this,"history");
//        String[] datas=content.split("\n");
//        for(int i=0;i<datas.length;i++){
//            Order order=Order.newInstanceWithData(datas[i]);
//            if(order !=null){
//                orders.add(order);
//            }
//        }
    }

    public void click(View view) {
        String note = editText.getText().toString();

        Order order = new Order();
        order.setNote(note);
        order.setMenuResults(menuResults);
        order.setStoreInfo((String) storeSpinner.getSelectedItem());
        //存在Local端
        order.pinInBackground();
        //上傳order物件
        order.saveEventually();

        orders.add(order);

        Utils.writeFile(this, "history", order.getJsonObject().toString());

        textView.setText(note);
        menuResults="";
        editText.setText("");

        setupListView();
    }

    public void goToMenu(View view){
        //使用意圖呼叫class
        Intent intent = new Intent();
        intent.setClass(this, DrinkMenuActivity.class);
        //REQUEST_CODE_DRINK_MENU_ACTIVITY 意圖回傳回來的requestCode
        startActivityForResult(intent, REQUEST_CODE_DRINK_MENU_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_DRINK_MENU_ACTIVITY){
            if(resultCode==RESULT_OK){
                menuResults = data.getStringExtra("results");
                Toast.makeText(this,"完成菜單",Toast.LENGTH_LONG).show();
            }
            if(resultCode==RESULT_CANCELED){
                Toast.makeText(this,"取消菜單",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Debug","Main Activity OnStrat");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug","Main Activity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug", "Main Activity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Debug", "Main Activity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Debug", "Main Activity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug", "Main Activity OnRestart");
    }
}
