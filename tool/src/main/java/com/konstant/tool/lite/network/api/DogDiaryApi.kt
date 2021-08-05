package com.konstant.tool.lite.network.api

import io.reactivex.Observable
import retrofit2.http.GET

interface DogDiaryApi {

    companion object {
        const val HOST = "https://api.oick.cn/"
    }

    @GET("dog/api.php/")
    fun getDogDiary(): Observable<String>

}