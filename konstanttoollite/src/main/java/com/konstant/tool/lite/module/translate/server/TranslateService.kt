package com.konstant.tool.lite.module.translate.server

import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.network.NetworkUtil
import com.konstant.tool.lite.util.MD5

/**
 * 时间：2018/8/2 16:56
 * 作者：吕卡
 * 描述：
 */
object TranslateService {

    // 翻译
    fun translate(url: String, originString: String, originType: String, resultType: String, appid: String,
                  secret: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        val md5 = MD5.md5(appid + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }
}