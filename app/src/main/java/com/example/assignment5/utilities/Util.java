package com.example.assignment5.utilities;

import android.content.Context;
import android.widget.Toast;
import com.example.assignment5.R;

public class Util {

    private String result ;

    public boolean validName(Context context, String studentName, String studentRoll, String studentClass){
        if(studentName.isEmpty()){
            result = context.getString(R.string.et_name_empty);
            displayToast(context,result);
            return false;
        }
        else if(!studentName.matches( "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")){
            result = context.getString(R.string.et_name_wrong);
            displayToast(context,result);
            return false;
        }
        if(studentRoll.isEmpty()){
            result = context.getString(R.string.et_roll_empty);
            displayToast(context,result);
            return false;
        }
        else if(!studentRoll.matches( "[0-9]*" )){
            result = context.getString(R.string.et_roll_wrong);
            displayToast(context,result);
            return false;
        }
        if(studentClass.isEmpty()){
            result = context.getString(R.string.et_class_empty);
            displayToast(context,result);
            return false;
        }
        else if(!studentClass.matches( "[a-zA-Z]*" )){
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
