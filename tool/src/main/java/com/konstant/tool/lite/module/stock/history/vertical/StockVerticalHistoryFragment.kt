package com.konstant.tool.lite.module.stock.history.vertical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.stock.history.StockViewModel
import kotlinx.android.synthetic.main.fragment_stock_history_vertical.*

class StockVerticalHistoryFragment : BaseFragment() {

    companion object {
        private const val KEY_YEAR = "YEAR"
        private const val KEY_MONTH = "MONTH"
        fun getInstance(year: Int, month: Int): StockVerticalHistoryFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_YEAR, year)
            bundle.putInt(KEY_MONTH, month)
            val fragment = StockVerticalHistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun getCurrentDate(): String {
        val year = arguments?.getInt(KEY_YEAR) ?: -1
        val month = arguments?.getInt(KEY_MONTH) ?: -1
        return "$year-$month"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_history_vertical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = arguments?.getInt(KEY_YEAR) ?: -1
        val month = arguments?.getInt(KEY_MONTH) ?: -1
        ViewModelProvider(requireActivity()).get(StockViewModel::class.java)
            .getStockMap()
            .observe(this, Observer {
                val stockList = it["$year-$month"] ?: mutableListOf()
                month_view.setData(year, month, stockList)
                chart_view.setData(stockList)
            })
    }

    override fun onResume() {
        super.onResume()
        val year = arguments?.getInt(KEY_YEAR) ?: -1
        val month = arguments?.getInt(KEY_MONTH) ?: -1
        val title = "$year - $month "
        setTitle(title)
    }

}