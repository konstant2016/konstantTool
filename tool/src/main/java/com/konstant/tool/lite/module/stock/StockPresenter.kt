package com.konstant.tool.lite.module.stock

import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class StockPresenter(private val dispose: CompositeDisposable) {

    fun getStockList(): List<StockData> {
        return StockManager.getStockList()
    }

    fun addStock(stockData: StockData, result: (List<StockData>) -> Unit, error: (String) -> Unit) {
        getStockDetail(listOf(stockData), result, error)
    }

    fun deleteStock(stockData: StockData) {
        StockManager.deleteStock(stockData)
    }

    fun getStockDetail(stockList: List<StockData>, result: (List<StockData>) -> Unit, error: (String) -> Unit) {
        val s = NetworkHelper.getStockDetail(stockList)
                .subscribe({
                    result.invoke(it)
                    StockManager.addStock(it)
                }, {
                    it.printStackTrace()
                    error.invoke(it.message ?: "")
                })
        dispose.add(s)
    }

}