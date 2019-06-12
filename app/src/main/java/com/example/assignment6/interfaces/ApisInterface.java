package com.example.assignment6.interfaces;

import com.example.assignment6.models.PostId;
import com.example.assignment6.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApisInterface {

    @GET("users")
    Call<List<User>> getAllData();

    @GET("posts")
    Call<List<PostId>> getComments(@Query("userId") int userId);
}
