package com.example.assignmentretry5.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private String name,roll,className;

    public Student(String name, String roll, String className) {
        this.name = name;
        this.roll = roll;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public String getClassName() {
        return className;
    }

    protected Student(Parcel in) {
        name = in.readString();
        roll = in.readString();
        className = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(roll);
        dest.writeString(className);
    }
}
