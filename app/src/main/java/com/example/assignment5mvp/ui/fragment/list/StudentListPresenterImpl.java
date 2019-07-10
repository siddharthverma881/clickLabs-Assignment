package com.example.assignment5mvp.ui.fragment.list;

import android.content.Context;

import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.util.Constants;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class StudentListPresenterImpl implements StudentListPresenter {

    private StudentListView mView;
    Constants constants = new Constants();

    public StudentListPresenterImpl(StudentListView studentListFragment){
        mView = studentListFragment;
    }

    @Override
    public void viewTapped(Student student,int dialoguePosition) {
            switch(dialoguePosition){
                case 0 :
                    mView.onViewPressed(student);
                    break;
                case 1 :
                    mView.onEditPressed(student);
                    break;
                case 2:
                    mView.onDeletePressed();
                    break;
            }
    }
}
