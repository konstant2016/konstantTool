package com.konstant.tool.lite.module.stock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.getThemColor
import com.konstant.tool.lite.data.bean.stock.StockHistory
import kotlinx.android.synthetic.main.fragment_stock_chart.*

/**
 * 时间：2021/6/5 21:00
 * 作者：吕卡
 * 备注：图表形式的fragment
 * 其它：图标的说明Api参考这里：http://www.zyiz.net/tech/detail-135529.html
 */

class StockChartFragment : Fragment() {

    private val mViewModel by lazy { ViewModelProvider(requireActivity()).get(StockViewModel::class.java) }

    companion object {
        private const val KEY_YEAR = "YEAR"
        private const val KEY_MONTH = "MONTH"
        fun getInstance(year: Int, month: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(KEY_YEAR, year)
            bundle.putInt(KEY_MONTH, month)
            val fragment = StockChartFragment()
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
            val list = mutableListOf<Entry>()
            stockList.forEach {
                list.add(Entry(it.day.toFloat(), it.total.toFloat()))
            }
            val dataSet = LineDataSet(list, "股票总市值")
            dataSet.setColor(requireActivity().getThemColor(R.attr.tool_main_text_color),80)
            dataSet.lineWidth = 2.0f
            dataSet.setDrawCircles(true)
            dataSet.setCircleColor(requireActivity().getThemColor(R.attr.tool_main_color))
            dataSet.circleHoleColor = requireActivity().getThemColor(R.attr.tool_main_text_color)
            dataSet.valueTextColor = requireActivity().getThemColor(R.attr.tool_main_text_color)
            val lineData = LineData(dataSet)
            view_chart.xAxis.setLabelCount(stockList.size,true)
            view_chart.data = lineData
            view_chart.xAxis.setDrawGridLines(false)
            view_chart.isHighlightPerTapEnabled = false
            view_chart.isHighlightPerDragEnabled = false
            view_chart.xAxis.textColor = requireActivity().getThemColor(R.attr.tool_main_text_color)
            view_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            view_chart.axisLeft.textColor = requireActivity().getThemColor(R.attr.tool_main_text_color)
            view_chart.axisRight.isEnabled = false
            view_chart.legend.isEnabled = false
            view_chart.description.isEnabled = false
            view_chart.setScaleEnabled(false)
            view_chart.invalidate()
        })

    }
}