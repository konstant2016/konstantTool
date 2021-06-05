package com.konstant.tool.lite.module.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.base.getThemColor
import com.konstant.tool.lite.data.bean.stock.StockHistory
import com.konstant.tool.lite.util.DateUtil
import kotlinx.android.synthetic.main.item_recycler_stock_history.view.*
import java.util.*

/**
 * 时间：6/2/21 11:18 AM
 * 作者：吕卡
 * 备注：股票历史页面的日历适配器
 */

class AdapterStockHistory(private val mYear: Int, private val mMonth: Int, private val mList: List<StockHistory>) : BaseRecyclerAdapter<AdapterStockHistory.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_stock_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val tvDate = holder.itemView.tv_date
        val tvTotal = holder.itemView.tv_total
        // 渲染上个月的几天
        if (position < getCurrentMonthStartWeek()) {
            tvDate.text = getLastMonthDayWithPosition(position).toString()
            tvTotal.text = ""
            val color = context.getThemColor(R.attr.tool_second_text_color)
            tvDate.setTextColor(color)
            tvDate.paint.isFakeBoldText = false
            return
        }
        // 渲染本月的日历
        val date = position - getCurrentMonthStartWeek() + 1
        val total = mList.find { it.day == date }?.total ?: 0.0
        tvDate.text = "$date"
        tvTotal.text = getTotalString(total)
        tvTotal.setTextColor(context.getThemColor(R.attr.tool_second_text_color))
        val color = context.getThemColor(R.attr.tool_main_text_color)
        tvDate.setTextColor(color)
        tvDate.paint.isFakeBoldText = true
    }

    override fun getItemCount(): Int {
        val currentMonthDayCount = getCurrentMonthDayCount()
        val startWeekIndex = getCurrentMonthStartWeek()
        return currentMonthDayCount + startWeekIndex
    }

    /**
     * 获取当前月份有几天
     */
    private fun getCurrentMonthDayCount(): Int {
        return DateUtil.getDayCountWithMonth(mYear, mMonth)
    }

    /**
     * 获取当月份第一天是周几
     * 周日为第0天
     */
    private fun getCurrentMonthStartWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, mYear)
        calendar.set(Calendar.MONTH, mMonth - 1)
        calendar.set(Calendar.DATE, 1)
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    /**
     * 计算指定位置，如果是显示上个月的日期的话，那么应该显示几号
     * 先算出日历左上角的日期：用上个月的总天数 - 本月第一天的的 day of week
     * 再根据position，计算出应该显示天
     */
    private fun getLastMonthDayWithPosition(position: Int): Int {
        val startDay = getLastMonthTotalDay() - getCurrentMonthStartWeek()
        return startDay + position
    }

    /**
     * 计算上个月有多少天，用来展示上个月从几号开始倒数
     */
    private fun getLastMonthTotalDay(): Int {
        val year = if (mMonth == 1) {
            mYear - 1
        } else {
            mYear
        }
        val month = if (mMonth == 1) {
            12
        } else {
            mMonth
        }
        return DateUtil.getDayCountWithMonth(year, month)
    }

    private fun getTotalString(total: Double): String {
        if (total == 0.0) return ""
        if (total > 100000000) {
            val s = String.format("%.2f", total / 100000000)
            return "$s 亿"
        }
        if (total > 1000000) {
            val s = String.format("%.2f", total / 1000000)
            return "$s 百万"
        }
        if (total > 10000) {
            val s = String.format("%.2f", total / 10000)
            return "$s 万"
        }
        return "${total.toInt()}元"
    }
}