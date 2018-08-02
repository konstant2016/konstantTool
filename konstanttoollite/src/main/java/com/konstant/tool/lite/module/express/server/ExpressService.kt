package com.konstant.tool.lite.module.express.server

import com.konstant.tool.lite.network.NetworkUtil
import com.konstant.tool.lite.network.UrlConstant

/**
 * 时间：2018/8/2 16:58
 * 作者：吕卡
 * 描述：
 */
object ExpressService {


    // 物流查询
    fun expressQuery(commanyId: String, num: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        val url = UrlConstant.EXPRESS_URL
        val request = ExpressRequest(commanyId, num).toString()
        NetworkUtil.get(url, request, callback)
    }
}