package com.example.user.simpleui;

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

    //    String selectedSex = "Male";
//    String name ="";
//    String sex = "";
    String drinkName = "black tea";


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
        //偵測按鈕切換
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId==R.id.maleRadioButton){
//                    selectedSex="Male";
//                }
//                if(checkedId==R.id.femaleRadioButton){
//                    selectedSex="Female";
//                }
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                drinkName = radioButton.getText().toString();
            }
        });

        checkBox = (CheckBox) findViewById(R.id.checkBox);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                changeTextView();
//            }
//        });

        listView = (ListView) findViewById(R.id.listView);
        setupListView();

        storeSpinner=(Spinner) findViewById(R.id.spinner);
        setupSpinner();
    }

    void setupSpinner(){
        //讀取Resouce內的String Array XML檔
        String[] data = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        storeSpinner.setAdapter(adapter);
    }

    void setupListView() {
//        String[] data = new String[]{"aa", "bb", "cc"};
//        //字串轉換器 使用預設item1
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinks);
//
//        List<Map<String,String>> data = new ArrayList<>();
//
//        for(int i=0;i<orders.size();i++){
//            Order order=orders.get(i);
//            Map<String,String> item =new HashMap<>();
//            //      key值,資料內容
//            item.put("note",order.note);
//            item.put("drinkname",order.drinkname);
//
//            data.add(item);
//        }
//
//        String[] from = {"note","drinkname"};
//        int[] to ={R.id.noteTextView,R.id.drinknameTextView};
//
//        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.listview_order_item,from,to);
        OrderAdapter adapter = new OrderAdapter(this,orders);
        listView.setAdapter(adapter);
    }

    public void click(View view) {
        String note = editText.getText().toString();
//        sex = selectedSex;
//        changeTextView();

        Order order = new Order();
        order.note = note;
        order.drinkname = drinkName;

        orders.add(order);

        textView.setText(drinkName);
        editText.setText("");

        setupListView();
    }

//    public void changeTextView(){
//        if(name.equals("")) //字串是物件 檢查是否為空字串要用equals
//            return;
//        if(checkBox.isChecked()){
//            textView.setText(name);
//        }else{
//            String content = name + " sex:" + sex;
//
//            textView.setText(content);
//        }
//
//    }
}
