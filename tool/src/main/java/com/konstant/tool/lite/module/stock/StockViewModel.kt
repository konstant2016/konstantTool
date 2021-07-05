package com.konstant.tool.lite.module.stock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konstant.tool.lite.data.bean.stock.StockHistory
import java.util.*
import kotlin.concurrent.thread

class StockViewModel : ViewModel() {

    private val mStockHistoryMap by lazy {
        MutableLiveData<MutableMap<String, MutableList<StockHistory>>>().also {
            getStockHistory()
        }
    }

    fun getStockMap(): MutableLiveData<MutableMap<String, MutableList<StockHistory>>> {
        return mStockHistoryMap
    }

    /**
     * 按照月份来分组，
     * key为 year-month
     * value 为该月份所包含的数据集合
     */
    private fun getStockHistory() {
        thread {
            val stockList = StockManager.getStockHistory()
            Collections.sort(stockList)
            val map = mutableMapOf<String, MutableList<StockHistory>>()
            stockList.forEach {
                val key = "${it.year}-${it.month}"
                if (map.contains(key)) {
                    val monthList = map[key]
                    monthList?.add(it)
                } else {
                    val monthList = mutableListOf<StockHistory>()
                    monthList.add(it)
                    map[key] = monthList
                }
            }
            mStockHistoryMap.postValue(map)
        }
    }
}