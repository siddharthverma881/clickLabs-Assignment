package com.example.android.assignment2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


//The activity file of splash screen
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //getting the id of the textView and setting its value which is defined in the strings.xml file
        TextView splashTextView = (TextView) findViewById(R.id.splash_tv);
        splashTextView.setText(getString(R.string.appName));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
          //for opening the login activity automatically after 3 seconds
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
          //for destroying the splash screen so that when back button is pressed in login page,splash screen does not open
                MainActivity.this.finish();
            }
        }, 3000);
    }
}
