package com.example.android.assignment4.models;

public class Student {
    private String mName,mRoll,mClassName;
    public Student(String mName,String mRoll,String mClassName){
        this.mName=mName;
        this.mRoll=mRoll;
        this.mClassName=mClassName;
    }
    public String getmName(){
        return mName;
    }
    public String getmRoll(){
        return mRoll;
    }
    public String getmClassName(){
        return mClassName;
    }
}
