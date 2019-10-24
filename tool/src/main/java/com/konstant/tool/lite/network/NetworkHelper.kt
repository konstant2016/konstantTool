package com.konstant.tool.lite.network

import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.network.api.ExpressApi
import com.konstant.tool.lite.network.api.TranslateApi
import com.konstant.tool.lite.network.api.UpdateApi
import com.konstant.tool.lite.network.api.WeatherApi
import com.konstant.tool.lite.network.config.FileDownloader
import com.konstant.tool.lite.network.config.RetrofitBuilder
import com.konstant.tool.lite.network.response.ExpressResponse
import com.konstant.tool.lite.network.response.TranslateResponse
import com.konstant.tool.lite.network.response.UpdateResponse
import com.konstant.tool.lite.network.response.WeatherResponse
import com.konstant.tool.lite.util.MD5
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

object NetworkHelper {

    // 获取物流信息
    fun getExpressInfo(expressNo: String): Observable<ExpressResponse> {
        return RetrofitBuilder
                .getApi(ExpressApi.HOST, ExpressApi::class.java)
                .getExpressInfo(expressNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // 百度翻译
    fun getTranslate(originMsg: String, originType: String, resultType: String): Observable<TranslateResponse> {
        val appid = "20180112000114653"
        val secret = "DMDnCBX70pAOKY84Q7oB"
        val sign = MD5.md5(appid + originMsg + System.currentTimeMillis() / 1000 + secret)
        val salt = System.currentTimeMillis() / 1000
        return RetrofitBuilder
                .getApi(TranslateApi.HOST, TranslateApi::class.java)
                .getTranslate(originMsg, originType, resultType, appid, salt.toString(), sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // 检查更新
    fun getUpdate(): Observable<UpdateResponse> {
        return RetrofitBuilder
                .getApi(UpdateApi.HOST, UpdateApi::class.java)
                .getUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // 查询天气
    fun getWeatherWithCode(cityCode: String): Observable<WeatherResponse> {
        return RetrofitBuilder
                .getApi(WeatherApi.HOST, WeatherApi::class.java)
                .getWeatherWithCode(cityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // 网速测试
    fun getSpeed(maxSize: Long, listener: FileDownloader.DownloadListener) {
        val path = "${KonApplication.context.externalCacheDir}${File.separator}fileStamp"
        FileDownloader.downloadFile(maxSize, path, FileDownloader.MainThreadDownloadListener(listener))
    }

}