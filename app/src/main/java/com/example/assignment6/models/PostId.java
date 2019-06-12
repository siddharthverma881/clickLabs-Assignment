package com.example.assignment6.models;

import com.google.gson.annotations.SerializedName;

public class PostId {

    private int id;

    private int userId;

    private String title;

    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
