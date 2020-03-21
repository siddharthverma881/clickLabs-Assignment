package com.example.siddharthlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelloWorldActivity extends AppCompatActivity {

    private TextView tvHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellow_world);

        tvHelloWorld = findViewById(R.id.tvHelloWorld);
    }
}
