package com.example.assignment5mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.assignment5mvp.model.Student;

import java.util.ArrayList;

public interface HomePresenter {

     void openAddStudentFragment(Student student,String action);

     void openStudentListFragment(Student student, String valueOfButton);

}
