package com.example.user.simpleui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class OrderDetailActivity extends AppCompatActivity implements GeoCodingTask.GeoCodingTaskResponse, GoogleApiClient.ConnectionCallbacks,LocationListener, GoogleApiClient.OnConnectionFailedListener {

    TextView noteTextView;
    TextView menuResultsTextView;
    TextView storeInfoTextView;
    ImageView staticMap;

    MapFragment mapFragment;
    GoogleMap googleMap;

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    LatLng stroeLocation;
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
        staticMap = (ImageView) findViewById(R.id.imageView);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);

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
            String[] storeInfos = storeInfo.split(",");
            if (storeInfos != null && storeInfos.length > 1) {
                String address = storeInfos[1];
                (new GeoCodingTask(this)).execute(address);
            }
        }
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
            }
        });
    }

    @Override
    public void responseWithGeocodingResults(LatLng latLng) {
        if (googleMap != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            googleMap.moveCamera(cameraUpdate);
            googleMap.animateCamera(cameraUpdate);  //放大縮小
            googleMap.addMarker(new MarkerOptions().position(latLng));  //紅色指標
            stroeLocation = latLng;
            createGoogleAPIClient();
        }
    }

    //劃路線
    private void createGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        }
    }

    private void createLocationRequest() {
        if (locationRequest == null) {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(googleApiClient!=null)
            googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient!=null)
            googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //    private static class GeoCodingTask extends AsyncTask<String, Void, Bitmap> {
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
//        public GeoCodingTask(ImageView imageView){
//            this.imageViewWeakReference=new WeakReference<ImageView>(imageView);
//        }
//    }
}
