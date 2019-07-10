package com.example.assignment5mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.ui.fragment.add.AddStudentFragment;
import com.example.assignment5mvp.ui.fragment.list.StudentListFragment;
import com.example.assignment5mvp.util.Constants;

import java.util.ArrayList;

public class HomePresenterImpl implements HomePresenter {

    private Constants constants = new Constants();
    private HomeView mView;

    HomePresenterImpl(HomeView view){
        mView = view;
    }

    @Override
    public void openAddStudentFragment(Student student,String action) {
        if(action.equals(constants.DIALOG_ACTION_ADD)) {
            mView.onAddStudent();
        }
        else if(action.equals(constants.DIALOG_ACTION_EDIT)){
            mView.onEditStudent(student);
        }
        else if(action.equals(constants.DIALOG_ACTION_VIEW)){
            mView.onViewStudent(student);
        }
    }

    @Override
    public void openStudentListFragment(Student student,String valueOfButton) {
        if(valueOfButton.equals(constants.VALUE_BTN_ADD)) {
            mView.addStudentSuccess(student);
        }
        else if(valueOfButton.equals(constants.VALUE_BTN_UPDATE)){
            mView.editStudentSuccess(student);
        }
    }
}
