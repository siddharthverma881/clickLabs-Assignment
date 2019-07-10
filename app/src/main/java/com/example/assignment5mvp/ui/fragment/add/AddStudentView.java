package com.example.assignment5mvp.ui.fragment.add;

import com.example.assignment5mvp.model.Student;

public interface AddStudentView {

    void editTextError();

    void onSuccess(Student student);

}
