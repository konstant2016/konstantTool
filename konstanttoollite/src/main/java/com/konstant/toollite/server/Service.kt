package com.konstant.toollite.server

import com.alibaba.fastjson.JSON
import com.konstant.toollite.server.request.TranslateRequest
import com.konstant.toollite.util.MD5
import com.konstant.toollite.util.NetworkUtil

/**
 * Created by konstant on 2018/4/4.
 */
object Service{

    // 翻译
    fun translate(url: String, originString: String, originType: String, resultType: String, appid: String,
                  secret: String, callback: (state: Boolean, data: String) -> Unit) {
        val md5 = MD5.md5("$appid" + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

}