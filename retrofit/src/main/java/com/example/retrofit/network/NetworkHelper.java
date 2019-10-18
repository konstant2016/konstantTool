package com.example.retrofit.network;

import com.example.retrofit.network.api.AccountApi;
import com.example.retrofit.network.api.AdvertisementApi;
import com.example.retrofit.network.api.VideoApi;
import com.example.retrofit.network.response.VideoListResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkHelper {

    private static NetworkHelper mHelper = new NetworkHelper();

    public static NetworkHelper getInstance() {
        return mHelper;
    }

    public Observable<String> getLogin(String username, String password) {
        return RetrofitConfig.getInstance().getApi(AccountApi.HOST, AccountApi.class)
                .login(username, password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getUserInfo(String username, String token) {
        return RetrofitConfig.getInstance().getApi(AccountApi.HOST, AccountApi.class)
                .getUserInfo(username, token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getAdvertisement(String username, String token) {
        return RetrofitConfig.getInstance().getApi(AccountApi.HOST, AdvertisementApi.class)
                .getAdvertise(username, token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VideoListResponse> getVideoList(String page, String type, String post_id) {
        return RetrofitConfig.getInstance().getApi(AccountApi.HOST, VideoApi.class)
                .getVideoList(page, type, post_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
