package com.example.locationpermission3.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.locationpermission3.util.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ForegroundService extends Service {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String mLatitude,mLongitude;
    private String mResult ="";
    Constants constants = new Constants();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent , int flags,int startId){
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLatitude = String.valueOf(location.getLatitude());
                                mLongitude = String.valueOf(location.getLongitude());
                                mResult = constants.FOREGROUND_SUCCESSFUL;
                                sendLocation(mLatitude,mLongitude,mResult);
                            } else {
                                mLatitude = mLongitude = "";
                                mResult = constants.FOREGROUND_FAIL;
                                sendLocation(mLatitude,mLongitude,mResult);
                            }
                        }
                    });
        return super.onStartCommand(intent,flags,startId);
    }

    private void sendLocation(String lat,String longi,String res){
        Intent resultIntent = new Intent(constants.BROADCAST_KEY);
        resultIntent.putExtra(constants.KEY_LATITUDE,lat);
        resultIntent.putExtra(constants.KEY_LONGITUDE,longi);
        resultIntent.putExtra(constants.KEY_FETCH_LOCATION,res);
        this.sendBroadcast(resultIntent);
    }
}