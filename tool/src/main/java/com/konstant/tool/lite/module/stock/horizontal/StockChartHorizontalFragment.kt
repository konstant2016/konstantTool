package com.konstant.tool.lite.module.stock.horizontal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.stock.StockViewModel
import kotlinx.android.synthetic.main.fragment_stock_chart.*

/**
 * 时间：2021/6/5 21:00
 * 作者：吕卡
 * 备注：图表形式的fragment
 * 其它：图标的说明Api参考这里：http://www.zyiz.net/tech/detail-135529.html
 */

class StockChartHorizontalFragment : Fragment() {

    private val mViewModel by lazy { ViewModelProvider(requireActivity()).get(StockViewModel::class.java) }

    companion object {
        private const val KEY_YEAR = "YEAR"
        private const val KEY_MONTH = "MONTH"
        fun getInstance(year: Int, month: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(KEY_YEAR, year)
            bundle.putInt(KEY_MONTH, month)
            val fragment = StockChartHorizontalFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = arguments?.getInt(KEY_YEAR) ?: 0
        val month = arguments?.getInt(KEY_MONTH) ?: 0
        mViewModel.getStockMap().observe(this, Observer {
            val stockList = it["$year-$month"]?: mutableListOf()
            view_chart.setData(stockList)
        })

    }
}