package com.example.maps.retrofit;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private String BASE_URL = "https://maps.googleapis.com/";

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
