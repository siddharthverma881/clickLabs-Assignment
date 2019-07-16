package com.example.locationpermission3.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class AllTimeService extends Service {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String mLatitude,mLongitude;

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
                            Log.i("location ---------","latitude :-" + mLatitude + " , longitude :- " + mLongitude);
                        } else {
                            mLatitude = mLongitude = "";
                            Log.i("location ---------","latitude :-" + mLatitude + " , longitude :- " + mLongitude);
                        }
                    }
                });
        return super.onStartCommand(intent,flags,startId);
    }
}
