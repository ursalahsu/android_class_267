package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    CheckBox checkBox;

    String selectedSex = "Male";
    String name ="";
    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Debug", "Hello LOG");

        textView = (TextView)findViewById(R.id.textview);
        textView.setText("Hello World");

        editText =(EditText)findViewById(R.id.editText);
        //監聽輸入
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //偵測enter按鈕 動作是按下按鈕
                if(keyCode== KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    click(v);
                    return true; //把enter攔截 避免顯示在editView中
                }
                return false;
            }
        });

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        //偵測按鈕切換
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.maleRadioButton){
                    selectedSex="Male";
                }
                if(checkedId==R.id.femaleRadioButton){
                    selectedSex="Female";
                }
            }
        });

        checkBox=(CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeTextView();
            }
        });
    }

    public void click(View view){
        name = editText.getText().toString();
        sex = selectedSex;
        changeTextView();
        editText.setText("");
    }

    public void changeTextView(){
        if(name.equals("")) //字串是物件 檢查是否為空字串要用equals
            return;
        if(checkBox.isChecked()){
            textView.setText(name);
        }else{
            String content = name + " sex:" + sex;

            textView.setText(content);
        }

    }
}
