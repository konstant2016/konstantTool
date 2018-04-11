package com.konstant.toollite.server

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.toollite.server.request.ExpressRequest
import com.konstant.toollite.server.request.MovieListRequest
import com.konstant.toollite.server.request.TranslateRequest
import com.konstant.toollite.server.request.WeatherRequest
import com.konstant.toollite.util.MD5
import com.konstant.toollite.util.NetworkUtil
import com.konstant.toollite.util.UrlConstant

/**
 * 描述:统一的访问服务器工具
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午8:01
 * 备注:
 */

object Service {

    // 翻译
    fun translate(context: Context, url: String, originString: String, originType: String, resultType: String, appid: String,
                  secret: String, callback: (state: Boolean, data: String) -> Unit) {
        val md5 = MD5.md5("$appid" + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.getInstance(context).get(url, param, callback)
    }

    // 物流查询
    fun expressQuery(context: Context, commanyId: String, num: String, callback: (state: Boolean, data: String) -> Unit) {
        val url = UrlConstant.EXPRESS_URL
        val request = ExpressRequest(commanyId, num).toString()
        NetworkUtil.getInstance(context).get(url, request, callback)
    }

    // 查询指定地址的天气
    fun locationToCID(context: Context, url: String, location: String, key: String, callback: (state: Boolean, data: String) -> Unit) {
        val request = WeatherRequest(location, key)
        val param = request.toString()
        NetworkUtil.getInstance(context).get(url, param, callback)
    }

    // 查询天气
    fun queryWeather(context: Context, directNo: String, callback: (state: Boolean, data: String) -> Unit) {
        "http://tqapi.mobile.360.cn/v4/101010600.json"
        val url = "${UrlConstant.WEATHER_URL}$directNo.json"
        NetworkUtil.getInstance(context).get(url, "", callback)
    }

    // 查询电影列表
    fun queryMovieList(context: Context,page: Int,callback: (state: Boolean, data: String) -> Unit) {
        val url = UrlConstant.MOVIE_LIST
        val request = MovieListRequest(page)
        NetworkUtil.getInstance(context).get(url,request.toString(),callback)
    }

}