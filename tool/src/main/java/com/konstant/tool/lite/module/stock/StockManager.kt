package com.konstant.tool.lite.module.stock

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.data.bean.stock.StockHistory
import com.konstant.tool.lite.util.FileUtil
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.collections.ArrayList

object StockManager {

    private const val NAME_STOCK = "StockName"
    private const val NAME_STOCK_HISTORY = "StockHistory"
    private val mStockList = ArrayList<StockData>()
    private val mHistoryList = ArrayList<StockHistory>()
    private val currentDate = AtomicReference("")
    // 用来控制横屏时，每个item走到resume时，显示上面的月份还是下面的图表
    private val showMoth = AtomicBoolean(true)

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

    fun saveToLocal(context: Context) {
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
        stockList.forEach { data ->
            if (!mStockList.any { it.number == data.number }) {
                mStockList.add(data)
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

    fun getStockHistory() = mHistoryList

    fun saveSyncStockList(context: Context, string: String) {
        val type = object : TypeToken<List<StockData>>() {}.type
        val array = Gson().fromJson<List<StockData>>(string, type)
        mStockList.clear()
        mStockList.addAll(array)
        saveStock(context)
    }

    fun saveSyncStockHistory(context: Context, string: String) {
        val type = object : TypeToken<List<StockHistory>>() {}.type
        val array = Gson().fromJson<List<StockHistory>>(string, type)
        mHistoryList.clear()
        mHistoryList.addAll(array)
        saveStockHistory(context)
    }

    fun getCurrentDate(): String {
        return currentDate.get()
    }

    fun setCurrentDate(date: String) {
        currentDate.set(date)
    }

    fun setShowMonth(show:Boolean){
        showMoth.set(show)
    }

    fun getShowMonth():Boolean{
        return showMoth.get()
    }

}