package com.example.retrofit.network.api;

import com.example.retrofit.App;

import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Observable;

public interface AdvertisementApi {

    String HOST = App.isPrd ? "这里写PRD环境的baseUrl" : "这里写PRE环境的baseUrl";

    @GET("/advertisement")
    Observable<String> getAdvertise(@Query("username") String username,
                             @Query("token") String password);

}
