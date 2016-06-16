package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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

    ArrayList<Order> orders = new ArrayList<>();
    String drinkName = "black tea";

    static int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Debug", "Hello LOG");

        textView = (TextView) findViewById(R.id.textview);
        textView.setText("Hello World");

        editText = (EditText) findViewById(R.id.editText);
        //監聽輸入
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //偵測enter按鈕 動作是按下按鈕
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    click(v);
                    return true; //把enter攔截 避免顯示在editView中
                }
                return false;
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        checkBox = (CheckBox) findViewById(R.id.checkBox);

        listView = (ListView) findViewById(R.id.listView);
        setupListView();

        storeSpinner= (Spinner) findViewById(R.id.spinner);
        setupSpinner();

        Log.d("Debug","Main Activity OnCreate");


    }

    void setupSpinner(){
        //讀取Resouce內的String Array XML檔
        String[] data = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        storeSpinner.setAdapter(adapter);
    }

    void setupListView() {
        OrderAdapter adapter = new OrderAdapter(this,orders);
        listView.setAdapter(adapter);
    }

    public void click(View view) {
        String note = editText.getText().toString();

        Order order = new Order();
        order.note = note;
        order.drinkname = drinkName;
        order.storeInfo= (String)storeSpinner.getSelectedItem();

        orders.add(order);

        textView.setText(drinkName);
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
                textView.setText(data.getStringExtra("results"));
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
