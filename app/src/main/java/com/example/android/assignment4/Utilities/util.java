package com.example.android.assignment4.Utilities;

import android.content.Context;
import android.widget.Toast;

import com.example.android.assignment4.R;

public class util {
    public boolean validName(Context context, String studentName, String studentRoll, String studentClass){
        if(studentName.isEmpty()){
            Toast.makeText(context,R.string.et_name_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!studentName.matches( "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")){
            Toast.makeText(context, R.string.et_name_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(studentRoll.isEmpty()){
            Toast.makeText(context,R.string.et_roll_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!studentRoll.matches( "[0-9]*" )){
            Toast.makeText(context,R.string.et_roll_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(studentClass.isEmpty()){
            Toast.makeText(context,R.string.et_class_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!studentClass.matches( "[a-zA-Z]*" )){
            Toast.makeText(context,R.string.et_class_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
