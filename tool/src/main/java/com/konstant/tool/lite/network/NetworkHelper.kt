package com.konstant.tool.lite.network

import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.network.api.*
import com.konstant.tool.lite.network.config.FileDownloader
import com.konstant.tool.lite.network.config.RetrofitBuilder
import com.konstant.tool.lite.network.response.*
import com.konstant.tool.lite.util.TranslateMD5
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
        val sign = TranslateMD5.getMd5(appid + originMsg + System.currentTimeMillis() / 1000 + secret)
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
        FileDownloader.downloadFile(maxSize, path, listener)
    }

    // 获取直播列表
    fun getTvLiveList(): Observable<TvLiveResponse> {
        return RetrofitBuilder
                .getApi(TvLiveApi.HOST, TvLiveApi::class.java)
                .getTvLiveList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // 查询股票价格
    fun getStockDetail(stockData: StockData): Observable<StockData> {
        return RetrofitBuilder.getApi(StockDetailApi.HOST, StockDetailApi::class.java)
                .getTodayStockDetail(stockData.number)
                .map {
                    val increase = it.p >= it.yc
                    return@map StockData(stockData.name, stockData.number, it.p, stockData.count, increase)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}