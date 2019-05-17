package com.example.android.assignment3;

public class Student {
    private String mName;
    private String mRoll;
    private String mClass;

    Student(String name, String roll, String cls){
        mName=name;
        mRoll=roll;
        mClass=cls;
    }

    public String getmName() {
        return mName;
    }

    public String getmRoll() {
        return mRoll;
    }

    public String getmClass() {
        return mClass;
    }
}
