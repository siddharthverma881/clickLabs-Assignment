package com.example.assignment5mvp.ui.fragment.list;

import com.example.assignment5mvp.model.Student;

public interface StudentListView {

     void onViewPressed(Student student);

     void onEditPressed(Student student);

     void onDeletePressed();

}
