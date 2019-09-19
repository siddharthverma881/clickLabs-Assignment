package com.example.maps.interfaces;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

        @GET("maps/api/directions/json?")
        Call<Map> getMap(
                @QueryMap Map<String,String> map
        );
}
