package com.example.android.assignment4.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.assignment4.R;
import com.example.android.assignment4.fragments.AddStudentFragment;

public class ViewStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * @param intent is recieved from the student list fragment which consists of a bundle
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddStudentFragment fragment = AddStudentFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.view_frame_layout, fragment);
        fragmentTransaction.commit();

    }
}
