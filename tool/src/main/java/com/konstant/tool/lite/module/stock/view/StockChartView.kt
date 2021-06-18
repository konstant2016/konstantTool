package com.konstant.tool.lite.module.stock.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.getThemColor
import com.konstant.tool.lite.data.bean.stock.StockHistory
import kotlinx.android.synthetic.main.view_chart.view.*


class StockChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chart, this, true)
    }

    fun setData(stockList: List<StockHistory>) {
        val list = mutableListOf<Entry>()
        stockList.forEach {
            if (it.total > 0){
                list.add(Entry(it.day.toFloat(), it.total.toFloat()))
            }
        }
        initBaseViews(list)
    }

    private fun initBaseViews(list: List<Entry>) {
        val dataSet = LineDataSet(list, "股票总市值")
        dataSet.setColor(context.getThemColor(R.attr.tool_main_text_color), 80)
        dataSet.lineWidth = 2.0f
        dataSet.setDrawCircles(true)
        dataSet.setCircleColor(context.getThemColor(R.attr.tool_main_color))
        dataSet.circleHoleColor = context.getThemColor(R.attr.tool_main_text_color)
        dataSet.valueTextColor = context.getThemColor(R.attr.tool_main_text_color)
        val lineData = LineData(dataSet)
        line_chart.xAxis.setLabelCount(list.size, true)
        line_chart.data = lineData
        line_chart.xAxis.setDrawGridLines(false)
        line_chart.isHighlightPerTapEnabled = false
        line_chart.isHighlightPerDragEnabled = false
        line_chart.xAxis.textColor = context.getThemColor(R.attr.tool_main_text_color)
        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        line_chart.axisLeft.textColor = context.getThemColor(R.attr.tool_main_text_color)
        line_chart.axisRight.isEnabled = false
        line_chart.legend.isEnabled = false
        line_chart.description.isEnabled = false
        line_chart.setScaleEnabled(false)
        line_chart.invalidate()
    }

}