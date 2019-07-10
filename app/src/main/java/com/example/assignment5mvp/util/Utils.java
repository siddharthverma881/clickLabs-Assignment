package com.example.assignment5mvp.util;

import android.content.Context;

import com.example.assignment5mvp.model.Student;

public class Utils {

    public boolean checkDetails(Student student){
        if(student.getName().isEmpty()){
            return false;
        }
        else if(!student.getName().matches( "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")){
            return false;
        }
        if(student.getRoll().isEmpty()){
            return false;
        }
        else if(!student.getRoll().matches( "[0-9]*" )){
            return false;
        }
        if(student.getClassName().isEmpty()){
            return false;
        }
        else if(!student.getClassName().matches( "[a-zA-Z]*" )){
            return false;
        }
        else {
            return true;
        }
    }
}
