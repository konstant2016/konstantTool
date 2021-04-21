package com.konstant.tool.lite.module.stock

import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class StockPresenter(private val dispose: CompositeDisposable) {

    fun getStockList(): List<StockData> {
        return StockManager.getStockList()
    }

    fun addStock(stockData: StockData, result: (StockData) -> Unit, error: (String) -> Unit) {
        val addResult = StockManager.addStock(stockData)
        if (!addResult) {
            error.invoke("请勿重复添加")
            return
        }
        getStockDetail(stockData, result, error)
    }

    fun deleteStock(stockData: StockData) {
        StockManager.deleteStock(stockData)
    }

    fun getStockDetail(stockData: StockData, result: (StockData) -> Unit, error: (String) -> Unit) {
        val s = NetworkHelper.getStockDetail(stockData)
                .subscribe({
                    result.invoke(it)
                }, {
                    it.printStackTrace()
                    error.invoke(it.message ?: "")
                })
        dispose.add(s)
    }

}