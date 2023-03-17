package com.konstant.tool.lite.module.stock.history

import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.network.NetworkHelper
import io.reactivex.Observable
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
        val list = mutableListOf<StockData>()
        val s = Observable.range(0, stockList.size)
            .flatMap {
                NetworkHelper.getStockDetail(stockList[it])
            }.map {
                list.add(it)
            }
            .subscribe({
                result.invoke(list)
                StockManager.addStock(list)
            }, {
                it.printStackTrace()
                error.invoke(it.message ?: "")
            })
        dispose.add(s)
    }

}