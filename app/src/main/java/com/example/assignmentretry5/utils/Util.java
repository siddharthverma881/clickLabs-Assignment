package com.example.assignmentretry5.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.assignmentretry5.R;
import com.example.assignmentretry5.models.Student;

public class Util {

    private String result ;

    public boolean checkDetails(Context context, Student student){
        if(student.getName().isEmpty()){
            result = context.getString(R.string.et_name_empty);
            displayToast(context,result);
            return false;
        }
        else if(!student.getName().matches( "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")){
            result = context.getString(R.string.et_name_wrong);
            displayToast(context,result);
            return false;
        }
        if(student.getRoll().isEmpty()){
            result = context.getString(R.string.et_roll_empty);
            displayToast(context,result);
            return false;
        }
        else if(!student.getRoll().matches( "[0-9]*" )){
            result = context.getString(R.string.et_roll_wrong);
            displayToast(context,result);
            return false;
        }
        if(student.getClassName().isEmpty()){
            result = context.getString(R.string.et_class_empty);
            displayToast(context,result);
            return false;
        }
        else if(!student.getClassName().matches( "[a-zA-Z]*" )){
            result = context.getString(R.string.et_class_wrong);
            displayToast(context,result);
            return false;
        }
        return true;
    }

    public void displayToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
