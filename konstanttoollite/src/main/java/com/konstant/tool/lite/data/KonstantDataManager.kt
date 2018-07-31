package com.konstant.tool.lite.data

import android.content.Context

/**
 * 描述:保存APP运行中的一些数据，避免频繁的IO读取，增加APP的流畅度
 * 创建人:菜籽
 * 创建时间:2018/4/25 下午4:00
 * 备注:
 */

object KonstantDataManager {

    fun onCreate(context: Context){
        ExpressManager.onCreate(context)
        CountryManager.onCreate(context)
    }

    fun onDestroy(context: Context){
        ExpressManager.onDestroy(context)
        CountryManager.onDestroy(context)
    }

}