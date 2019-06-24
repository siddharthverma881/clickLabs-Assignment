package com.example.assignmentretry5.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assignmentretry5.R;
import com.example.assignmentretry5.fragments.AddStudentFragment;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.utils.Constants;

public class ViewStudentActivity extends AppCompatActivity {

    Constants constants = new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        Student student = intent.getParcelableExtra(constants.VIEW_INTENT_KEY);
        bundle.putParcelable(constants.VIEW_BUNDLE_KEY,student);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddStudentFragment fragment = AddStudentFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.view_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
