package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/6/23.
 */
public class SampleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //註冊類別
        ParseObject.registerSubclass(Order.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jzKooj3ryei2knZRFszEHucDRSYVMpsNwHDv4Du7")
                .server("https://parseapi.back4app.com/")
                .clientKey("UTtPLkkWZuNHTLrYJ6g4oLdaVytU2PoCKrYuz4Z2")
                        .enableLocalDataStore()
        .build()
        );
    }
}