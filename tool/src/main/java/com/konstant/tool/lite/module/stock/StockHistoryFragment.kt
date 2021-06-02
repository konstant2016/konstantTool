package com.konstant.tool.lite.module.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.data.bean.stock.StockHistory
import kotlinx.android.synthetic.main.fragment_stock_history.*

class StockHistoryFragment : BaseFragment() {

    private var mYear = 0
    private var mMonth = 0
    private var mStockList = mutableListOf<StockHistory>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterStockHistory(mYear, mMonth, mStockList)
        recycler_view.layoutManager = GridLayoutManager(context,7)
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val title = "$mYear - $mMonth "
        setTitle(title)
    }

    fun setData(year: Int, month: Int, stockList: List<StockHistory>) {
        mStockList.clear()
        mStockList.addAll(stockList)
        mYear = year
        mMonth = month
    }
}