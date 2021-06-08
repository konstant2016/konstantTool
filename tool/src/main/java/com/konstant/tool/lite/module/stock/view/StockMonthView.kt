package com.konstant.tool.lite.module.stock.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.data.bean.stock.StockHistory
import kotlinx.android.synthetic.main.view_month.view.*

class StockMonthView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_month, this, true)
    }

    fun setData(year: Int, month: Int, stockList: List<StockHistory>) {
        initBaseViews(year, month, stockList)
    }

    private fun initBaseViews(year: Int, month: Int, stockList: List<StockHistory>) {
        val adapter = AdapterStockMonth(year, month, stockList)
        recycler_view.layoutManager = GridLayoutManager(context, 7)
        recycler_view.adapter = adapter
    }
}