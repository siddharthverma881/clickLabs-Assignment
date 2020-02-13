package com.example.android.assignment3;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    /**
     * @param time is the time after which the splash screen will be finished and next screen will be opened
     */
    private static int time=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, StudentListActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, time);
    }
}
