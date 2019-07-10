package com.example.assignment5mvp.ui.fragment.add;

import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.util.Utils;

public class AddStudentPresenterImpl implements AddStudentPresenter {

    private AddStudentView mView;
    private Utils utils = new Utils();

    AddStudentPresenterImpl(AddStudentView addStudentView){
        mView = addStudentView;
    }

    @Override
    public void buttonTapped(Student student) {
        if(utils.checkDetails(student)) {
            mView.onSuccess(student);
        }
        else{
            mView.editTextError();
        }
    }
}
