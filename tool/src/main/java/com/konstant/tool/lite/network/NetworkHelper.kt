package com.konstant.tool.lite.network

import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.module.user.UserInfo
import com.konstant.tool.lite.network.api.*
import com.konstant.tool.lite.network.config.FileDownloader
import com.konstant.tool.lite.network.config.RetrofitBuilder
import com.konstant.tool.lite.network.response.*
import com.konstant.tool.lite.util.TranslateMD5
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.nio.charset.Charset

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
            .getApi(BmobApi.HOST, BmobApi::class.java)
            .getTvLiveList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /**
     * type：上海还是深圳（sh或者sz）
     * code：股票代码
     */
    fun getStockDetail(data: StockData): Observable<StockData> {
        return RetrofitBuilder.getApi(StockDetailApi.DETAIL_HOST, StockDetailApi::class.java)
            .getTodayStockDetail(data.number)
            .map {
                val s = String(it.bytes(), Charset.forName("gb2312"))
                val split = s.split("~")
                val name = split[1]
                val price = split[3].toDouble()
                val increase = price > split[4].toDouble()
                data.name = name
                data.price = price
                data.isIncrease = increase
                return@map data
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 获取股票当前的涨跌情况
     */
    fun getStockBrief(number: String): Observable<Double> {
        return RetrofitBuilder.getApi(StockDetailApi.BRIEF_HOST, StockDetailApi::class.java)
            .getTodayStockBrief(number)
            .map {
                val s = String(it.bytes(), Charset.forName("gb2312"))
                val split = s.split("~")
                return@map split[5].toDouble()
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 获取舔狗日记
    fun getDogDiary(): Observable<String> {
        return RetrofitBuilder
            .getApi(DogDiaryApi.HOST, DogDiaryApi::class.java)
            .getDogDiary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 上传用户信息
    fun uploadUserInfo(userInfo: UserInfo): Observable<Boolean> {
        return RetrofitBuilder.getApi(BmobApi.HOST, BmobApi::class.java)
            .uploadUserInfo(userInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.code() == 200 }
    }

    // 获取用户信息
    fun getUserInfo(): Observable<UserInfo> {
        return RetrofitBuilder.getApi(BmobApi.HOST, BmobApi::class.java)
            .getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}