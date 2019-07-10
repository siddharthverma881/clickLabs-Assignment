package com.example.assignment5mvp.model;

public class Student {

    private String name, roll, className;

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
}
