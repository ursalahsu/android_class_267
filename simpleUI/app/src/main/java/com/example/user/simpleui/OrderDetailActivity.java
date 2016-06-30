package com.example.user.simpleui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class OrderDetailActivity extends AppCompatActivity {

    TextView noteTextView;
    TextView menuResultsTextView;
    TextView storeInfoTextView;
    ImageView staticMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");
        String menuResults = intent.getStringExtra("menuResults");
        String storeInfo = intent.getStringExtra("storeInfo");

//        Log.d("debug",note);
//        Log.d("debug",menuResults);
//        Log.d("debug",storeInfo);
        noteTextView = (TextView) findViewById(R.id.noteTextView);
        menuResultsTextView = (TextView) findViewById(R.id.menuResultTextView);
        storeInfoTextView = (TextView) findViewById(R.id.storeInfoTextView);
        staticMap=(ImageView)findViewById(R.id.imageView);

        if (note != null)
            noteTextView.setText(note);

        if (storeInfo != null)
            storeInfoTextView.setText(storeInfo);

        String text = "";
        if (menuResults != null) {
            try {
                JSONArray jsonArray = new JSONArray(menuResults);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String lNumber = String.valueOf(object.getInt("lNumber"));
                    String mNumber = String.valueOf(object.getInt("mNumber"));
                    text += object.getString("drinkName") + ":大杯" + lNumber + "杯   中杯" + mNumber + "杯\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            menuResultsTextView.setText(text);

//            //匿名函式
//            //Thread會跟Activity綁在一起 CG可能不會對Activity清除 而造成memory lock
//            //最好不要這樣寫
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }).run();
            String[] storeInfos=storeInfo.split(",");
            if(storeInfos !=null && storeInfos.length>1){
                String address = storeInfos[1];
                (new GeoGodingTask(staticMap)).execute(address);
            }

        }
    }


//    private static class GeoGodingTask extends AsyncTask<String, Void, Bitmap> {
//
//        WeakReference<ImageView> imageViewWeakReference;
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String address=params[0];
//
//            double[] latlng = Utils.getLatLngFromGoogleMapAPI(address);
//            if(latlng!=null){
//                Log.d("debug",String.valueOf(latlng[0]));
//                Log.d("debug",String.valueOf(latlng[1]));
//            }
//
//            return Utils.getStaticMap(latlng);
//        }
//        @Override
//        protected void onPostExecute(Bitmap bitmap){
//            super.onPostExecute(bitmap);
//            if(imageViewWeakReference.get()!=null){
//                ImageView imageView=imageViewWeakReference.get();
//                imageView.setImageBitmap(bitmap);
//            }
//        }
//
//        public GeoGodingTask(ImageView imageView){
//            this.imageViewWeakReference=new WeakReference<ImageView>(imageView);
//        }
//    }
}
