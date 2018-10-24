package com.konstant.tool.lite.module.beauty

import com.konstant.tool.lite.network.NetworkUtil

/**
 * 时间：2018/8/2 16:52
 * 作者：吕卡
 * 描述：
 */
object BeautyService {

    fun getBeautyData(url: String, callback: (Boolean, ByteArray) -> Unit) {
        NetworkUtil.get(url, callback = callback)
    }

}