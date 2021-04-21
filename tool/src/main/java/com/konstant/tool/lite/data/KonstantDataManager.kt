package com.konstant.tool.lite.data

import android.content.Context
import com.konstant.tool.lite.main.FunctionCollectorManager
import com.konstant.tool.lite.module.express.ExpressManager
import com.konstant.tool.lite.module.skip.AutoSkipManager
import com.konstant.tool.lite.module.stock.StockManager
import com.konstant.tool.lite.module.weather.data.CountryManager

/**
 * 描述:保存APP运行中的一些数据，避免频繁的IO读取，增加APP的流畅度
 * 创建人:菜籽
 * 创建时间:2018/4/25 下午4:00
 * 备注:
 */

object KonstantDataManager {

    fun onCreate(context: Context){
        AreaManager.onCreate(context)
        ExpressManager.onCreate(context)
        CountryManager.onCreate(context)
        FunctionCollectorManager.onCreate(context)
        AutoSkipManager.onCreate(context)
        StockManager.onCreate(context)
    }

    fun onDestroy(context: Context){
        ExpressManager.onDestroy(context)
        CountryManager.onDestroy(context)
        FunctionCollectorManager.onDestroy(context)
        AutoSkipManager.onDestroy(context)
        StockManager.onDestroy(context)
    }

}