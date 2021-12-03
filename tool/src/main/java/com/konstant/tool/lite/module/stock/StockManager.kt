package com.konstant.tool.lite.module.stock

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.data.bean.stock.StockHistory
import com.konstant.tool.lite.util.FileUtil
import java.util.*
import kotlin.collections.ArrayList

object StockManager {

    private const val NAME_STOCK = "StockName"
    private const val NAME_STOCK_HISTORY = "StockHistory"
    private val mStockList = ArrayList<StockData>()
    private val mHistoryList = ArrayList<StockHistory>()

    fun onCreate(context: Context) {
        createStock(context)
        createHistory(context)
    }

    private fun createStock(context: Context) {
        val temp = FileUtil.readFileFromFile(context, NAME_STOCK)
        if (temp.isNotEmpty()) {
            val type = object : TypeToken<List<StockData>>() {}.type
            val array = Gson().fromJson<List<StockData>>(String(temp), type)
            mStockList.clear()
            mStockList.addAll(array)
        }
    }

    private fun createHistory(context: Context) {
        val temp = FileUtil.readFileFromFile(context, NAME_STOCK_HISTORY)
        if (temp.isNotEmpty()) {
            val type = object : TypeToken<List<StockHistory>>() {}.type
            val array = Gson().fromJson<List<StockHistory>>(String(temp), type)
            mHistoryList.clear()
            mHistoryList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        saveStock(context)
        saveStockHistory(context)
    }

    private fun saveStock(context: Context) {
        val json = Gson().toJson(mStockList)
        FileUtil.saveFileToFile(context, NAME_STOCK, json.toByteArray())
    }

    private fun saveStockHistory(context: Context) {
        val json = Gson().toJson(mHistoryList)
        FileUtil.saveFileToFile(context, NAME_STOCK_HISTORY, json.toByteArray())
    }

    fun addStock(stockList: List<StockData>) {
        stockList.forEach {
            if (!mStockList.contains(it)) {
                mStockList.add(it)
            }
        }
    }

    fun deleteStock(stockData: StockData) {
        val index = mStockList.indexOfFirst { it.number == stockData.number }
        if (index < 0) return
        mStockList.removeAt(index)
    }

    fun getStockList() = mStockList

    fun saveTodayTotal(total: Double) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        mHistoryList.find { it.year == year && it.month == month && it.day == day }?.let {
            it.total = total
            return
        }
        val data = StockHistory(year, month, day, total)
        mHistoryList.add(data)
    }

//    fun getStockHistory() = mHistoryList
    fun getStockHistory():ArrayList<StockHistory> {
        val gson = "[{\"day\":20,\"month\":10,\"total\":2416670.15,\"year\":2021},{\"day\":20,\"month\":10,\"total\":267989.3,\"year\":2021},{\"day\":21,\"month\":10,\"total\":2396385.635,\"year\":2021},{\"day\":22,\"month\":10,\"total\":2417893.91,\"year\":2021},{\"day\":23,\"month\":10,\"total\":2417893.91,\"year\":2021},{\"day\":25,\"month\":10,\"total\":2404798.6450000005,\"year\":2021},{\"day\":26,\"month\":10,\"total\":2356926.975,\"year\":2021},{\"day\":27,\"month\":10,\"total\":2314548.83,\"year\":2021},{\"day\":28,\"month\":10,\"total\":2293878.45,\"year\":2021},{\"day\":29,\"month\":10,\"total\":2311706.4,\"year\":2021},{\"day\":31,\"month\":10,\"total\":2311706.4,\"year\":2021},{\"day\":1,\"month\":11,\"total\":2322466.8,\"year\":2021},{\"day\":2,\"month\":11,\"total\":2338239.565,\"year\":2021},{\"day\":3,\"month\":11,\"total\":2285139.2,\"year\":2021},{\"day\":4,\"month\":11,\"total\":2307711.87,\"year\":2021},{\"day\":5,\"month\":11,\"total\":2348135.61,\"year\":2021},{\"day\":8,\"month\":11,\"total\":2357085.35,\"year\":2021},{\"day\":9,\"month\":11,\"total\":2340621.815,\"year\":2021},{\"day\":10,\"month\":11,\"total\":2334077.705,\"year\":2021},{\"day\":11,\"month\":11,\"total\":2301418.4850000003,\"year\":2021},{\"day\":12,\"month\":11,\"total\":2357759.5199999996,\"year\":2021},{\"day\":13,\"month\":11,\"total\":2357759.5199999996,\"year\":2021},{\"day\":14,\"month\":11,\"total\":2357759.5199999996,\"year\":2021},{\"day\":15,\"month\":11,\"total\":2392894.35,\"year\":2021},{\"day\":16,\"month\":11,\"total\":2480157.7,\"year\":2021},{\"day\":17,\"month\":11,\"total\":2450787.8899999997,\"year\":2021},{\"day\":18,\"month\":11,\"total\":2451635.6,\"year\":2021},{\"day\":19,\"month\":11,\"total\":2439776.935,\"year\":2021},{\"day\":21,\"month\":11,\"total\":0.0,\"year\":2021},{\"day\":22,\"month\":11,\"total\":0.0,\"year\":2021},{\"day\":23,\"month\":11,\"total\":2437851.12,\"year\":2021},{\"day\":24,\"month\":11,\"total\":2442338.91,\"year\":2021},{\"day\":25,\"month\":11,\"total\":2442398.365,\"year\":2021},{\"day\":26,\"month\":11,\"total\":2426092.335,\"year\":2021},{\"day\":27,\"month\":11,\"total\":2426092.335,\"year\":2021},{\"day\":28,\"month\":11,\"total\":2426092.335,\"year\":2021},{\"day\":29,\"month\":11,\"total\":2436117.21,\"year\":2021},{\"day\":30,\"month\":11,\"total\":2455674.755,\"year\":2021},{\"day\":1,\"month\":12,\"total\":2399298.875,\"year\":2021},{\"day\":2,\"month\":12,\"total\":2444173.58,\"year\":2021},{\"day\":3,\"month\":12,\"total\":2522298.95,\"year\":2021}]"
        val type = object :TypeToken<ArrayList<StockHistory>>(){}.type
        return Gson().fromJson(gson,type)
    }

    fun saveSyncStockList(context: Context,string: String){
        val type = object : TypeToken<List<StockData>>() {}.type
        val array = Gson().fromJson<List<StockData>>(string, type)
        mStockList.clear()
        mStockList.addAll(array)
        saveStock(context)
    }

    fun saveSyncStockHistory(context: Context,string: String){
        val type = object : TypeToken<List<StockHistory>>() {}.type
        val array = Gson().fromJson<List<StockHistory>>(string, type)
        mHistoryList.clear()
        mHistoryList.addAll(array)
        saveStockHistory(context)
    }
}