package com.example.android.assignment3.Utilities;

import android.content.Context;
import android.widget.Toast;
import com.example.android.assignment3.R;
import com.example.android.assignment3.Student;

import java.util.Comparator;

public class util {

    public boolean validName(Context context, String studentName, String studentRoll, String studentClass){
        if(studentName.isEmpty()){
            Toast.makeText(context,R.string.et_name_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!studentName.matches( "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}")){
            Toast.makeText(context,R.string.et_name_wrong,Toast.LENGTH_SHORT).show();
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

    public static Comparator<Student> getCompByName(){
        Comparator comp = new Comparator<Student>(){
            @Override
            public int compare(Student s1, Student s2)
            {
                return s1.getmName().compareTo(s2.getmName());
            }
        };
        return comp;
    }
    public static Comparator<Student> getCompById(){
        Comparator comp = new Comparator<Student>(){
            @Override
            public int compare(Student s1, Student s2)
            {
                int number1 =Integer.parseInt(s1.getmRoll());
                int number2 =Integer.parseInt(s2.getmRoll());
                if(number1>number2)
                    return 1;
                else if(number2>number1)
                    return -1;
                else
                    return 0;
            }
        };
        return comp;
    }
}
