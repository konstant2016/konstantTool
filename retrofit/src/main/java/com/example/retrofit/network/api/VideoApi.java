package com.example.retrofit.network.api;

import com.example.retrofit.network.response.VideoListResponse;

import retrofit2.http.Query;
import io.reactivex.Observable;

public interface VideoApi {

    String HOTS = "http://baobab.kaiyanapp.com/api/";

    Observable<VideoListResponse> getVideoList(@Query("page") String page,
                                               @Query("type") String type,
                                               @Query("post_id") String post_id);

}
