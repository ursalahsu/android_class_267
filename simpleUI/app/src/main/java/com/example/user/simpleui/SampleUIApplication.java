package com.example.user.simpleui;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
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
        ParseObject.registerSubclass(Drink.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("jzKooj3ryei2knZRFszEHucDRSYVMpsNwHDv4Du7")
                        .server("https://parseapi.back4app.com/")
                        .clientKey("UTtPLkkWZuNHTLrYJ6g4oLdaVytU2PoCKrYuz4Z2")

//                        .applicationId("76ee57f8e5f8bd628cc9586e93d428d5")
//                        .server("http://parseserver-ps662-env.us-east-1.elasticbeanstalk.com/parse/")

                        .enableLocalDataStore()
                        .build()
        );

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);

        ParseFacebookUtils.initialize(this);
    }
}
