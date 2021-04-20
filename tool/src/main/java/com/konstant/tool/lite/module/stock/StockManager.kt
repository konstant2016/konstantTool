package com.konstant.tool.lite.module.stock

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

object StockManager {

    private const val NAME_STOCK = "StockName"
    private val mStockList = ArrayList<StockData>()

    fun onCreate(context: Context) {
        val temp = FileUtil.readFileFromFile(context, NAME_STOCK)
        if (temp.isNotEmpty()) {
            val type = object : TypeToken<List<StockData>>() {}.type
            val array = Gson().fromJson<List<StockData>>(String(temp), type)
            mStockList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        val json = Gson().toJson(mStockList)
        Executors.newSingleThreadExecutor()
                .execute {
                    FileUtil.saveFileToFile(context, NAME_STOCK, json.toByteArray())
                }
    }

    fun addStock(stockData: StockData):Boolean {
        return mStockList.add(stockData)
    }

    fun deleteStock(stockData: StockData){
        mStockList.remove(stockData)
    }

    fun getStockList() = mStockList

}