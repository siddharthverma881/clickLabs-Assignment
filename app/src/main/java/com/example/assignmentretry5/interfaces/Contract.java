package com.example.assignmentretry5.interfaces;

import com.example.assignmentretry5.models.Student;

import java.util.ArrayList;

public interface Contract {

     void asyncTaskStatus(Student student,String result);

     void showAllData(ArrayList<Student> studentArrayList);
}
