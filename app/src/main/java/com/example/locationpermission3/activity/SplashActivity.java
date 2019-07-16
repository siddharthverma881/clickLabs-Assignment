package com.example.locationpermission3.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.locationpermission3.R;

public class SplashActivity extends AppCompatActivity {

    private int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LocationActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, time);
    }
}
