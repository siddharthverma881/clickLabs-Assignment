package com.example.android.recyclertry;

public class User {
    private String name;
    private String age;
    private String phno;
    private int likes;
    private float fstars;
//assigning the name,age and phone number to the user constructor
    public User(String name, String age, String phno){
        this.name=name;
        this.age=age;
        this.phno=phno;
    }
    public void setLikes(int count){
        this.likes=count;
    }
    public void setStars(float count){
        this.fstars=count;
    }
    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getPhno(){
        return phno;
    }
    public int getLikes(){
        return likes;
    }
    public float getStars(){
        return fstars;
    }

}
