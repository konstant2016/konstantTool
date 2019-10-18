package com.example.retrofit.network.api;

import com.example.retrofit.App;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountApi {

    String HOST = App.isPrd ? "这里写PRD环境的baseUrl" : "这里写PRE环境的baseUrl";

    @GET("/login")
    Observable<String> login(@Query("username") String username,
                             @Query("password") String password);

    @GET("/info")
    Observable<String> getUserInfo(@Query("username") String username,
                                   @Query("token") String token);

}
