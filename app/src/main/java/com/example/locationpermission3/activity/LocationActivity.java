package com.example.locationpermission3.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.locationpermission3.R;
import com.example.locationpermission3.service.AllTimeService;
import com.example.locationpermission3.service.ForegroundService;
import com.example.locationpermission3.util.Constants;

public class LocationActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTvLocation;
    private String latitude,longitude;
    private IntentFilter mIntent = new IntentFilter();
    private BroadcastReceiver mReceiver;
    private boolean mBackgroundLocationPermissionApproved;
    private Constants constants = new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mButton = findViewById(R.id.location_btn);
        mTvLocation = findViewById(R.id.location_tv);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForegroundGranted();
            }
        });
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String result = intent.getStringExtra(constants.KEY_FETCH_LOCATION);
                if(result.contentEquals(constants.FOREGROUND_SUCCESSFUL)){
                    longitude = intent.getStringExtra(constants.KEY_LONGITUDE);
                    latitude = intent.getStringExtra(constants.KEY_LATITUDE);
                    mTvLocation.setText(latitude + "," + longitude);
                    checkBackgroundPermission();
                }
                else{
                    mTvLocation.setText(result);
                }
            }
        };
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mIntent = new IntentFilter(constants.BROADCAST_KEY);
        registerReceiver(mReceiver,mIntent);
    }

    @Override
    protected void onPause() {
        if(mIntent != null) {
            unregisterReceiver(mReceiver);
            mIntent = null;
        }
        super.onPause();
    }

    private void checkForegroundGranted(){
        boolean permissionAccessCoarseLocationApproved = ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permissionAccessCoarseLocationApproved) {
            Intent intent = new Intent(LocationActivity.this, ForegroundService.class);
            startService(intent);
        }
        else {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    private void checkBackgroundPermission(){
        mBackgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (mBackgroundLocationPermissionApproved) {
            Intent intent1 = new Intent(LocationActivity.this, AllTimeService.class);
            startService(intent1);
        } else {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 2);
        }
    }
}
