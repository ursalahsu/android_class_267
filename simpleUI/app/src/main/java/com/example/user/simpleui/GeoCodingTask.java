package com.example.user.simpleui;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/6/30.
 */
public class GeoCodingTask extends AsyncTask<String, Void, double[]>{
    WeakReference<GeoCodingTaskResponse> geoCodingTaskResponseWeakReference;

    @Override
    protected double[] doInBackground(String... params) {
        String address = params[0];
        double[] latlng =Utils.getLatLngFromGoogleMapAPI(address);
        return latlng;
    }

    @Override
    protected void onPostExecute(double[] doubles) {
        super.onPostExecute(doubles);
        if(geoCodingTaskResponseWeakReference.get()!=null){
            GeoCodingTaskResponse response = geoCodingTaskResponseWeakReference.get();
            response.responseWithGeoCodingResults(new LatLng(doubles[0],doubles[1]));
        }
    }

    public GeoCodingTask(GeoCodingTaskResponse geoCodingTaskResponse){
        geoCodingTaskResponseWeakReference = new WeakReference<GeoCodingTaskResponse>(geoCodingTaskResponse);
    }

    interface GeoCodingTaskResponse{
        void responseWithGeoCodingResults(LatLng latLng);
    }

}


