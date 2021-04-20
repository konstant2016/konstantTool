package com.konstant.tool.lite.module.stock

import android.os.Bundle
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.bean.stock.StockData
import kotlinx.android.synthetic.main.title_layout.*

class StockActivity : BaseActivity() {

    private val mStockList = mutableListOf<StockData>()
    private val mAdapter by lazy { AdapterStock(mStockList) }
    private val mPresenter by lazy { StockPresenter(mDisposable) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        getStockList()
    }

    private fun initViews() {
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener {  }
    }

    private fun getStockList(){
        val stockList = mPresenter.getStockList()
        if (stockList.isNotEmpty()){
            stockList.forEach {
                getStockDetail(it)
            }
        }
    }

    private fun getStockDetail(stockData: StockData){
        mPresenter.getStockDetail(stockData,{
            if (mStockList.contains(it)) return@getStockDetail
            mStockList.add(it)
            mAdapter.notifyDataSetChanged()
        },{

        })
    }

    private fun showAddDialog(){

    }

}