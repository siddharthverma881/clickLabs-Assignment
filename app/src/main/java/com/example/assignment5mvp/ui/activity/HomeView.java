package com.example.assignment5mvp.ui.activity;

import com.example.assignment5mvp.model.Student;

public interface HomeView {

    void onAddStudent();

    void onEditStudent(Student student);

    void onViewStudent(Student student);

    void addStudentSuccess(Student student);

    void editStudentSuccess(Student student);
}
