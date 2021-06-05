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