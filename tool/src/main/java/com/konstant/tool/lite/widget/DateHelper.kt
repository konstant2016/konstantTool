package com.konstant.tool.lite.widget

import android.text.TextUtils
import androidx.annotation.Keep
import cn.hutool.core.date.ChineseDate
import com.konstant.tool.lite.util.DateUtil
import java.io.Serializable
import java.util.*

object DateHelper {

    @Keep
    data class ChineseHoliday(
        val year: Int,
        val month: Int,
        val day: Int,
        val holiday: String,
    ) : Serializable

    private val chineseHolidayList = mutableListOf<ChineseHoliday>()

    fun setChineseHoliday(list: List<ChineseHoliday>) {
        chineseHolidayList.clear()
        chineseHolidayList.addAll(list)
    }

    open class CalenderItem(
        val currentMonth: Boolean,     // 类型：周，上个月，本月
        val title: String,      // 日期
        val subTitle: String,   // 农历
        val currentDay: Boolean, // 是否是当天
        val isHoliday: Boolean    // 是否是节气或者假期
    )

    /**
     * 第一行显示的周排列
     * 第二行开始显示日期
     */
    fun getStringWithPosition(position: Int): CalenderItem {
        /**
         * 计算第一行，开始的位置，这个地方显示的是上个月的最后几天
         */
        if (position < getCurrentMonthStartWeek()) {
            val date = getLastMonthDayWithPosition(position)
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date)
            val holiday = getHolidayName(calendar)
            val isHoliday = !TextUtils.isEmpty(holiday)
            val lunar = if (isHoliday) holiday else getLunarDate(calendar)
            return CalenderItem(false, date.toString(), lunar, false, isHoliday)
        }
        /**
         * 显示本月的具体数据
         */
        val date = position - getCurrentMonthStartWeek()
        if (date <= getCurrentMonthTotalDay()) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, date + 1)
            val holiday = getHolidayName(calendar)
            val isHoliday = !TextUtils.isEmpty(holiday)
            val lunar = if (isHoliday) holiday else getLunarDate(calendar)
            return CalenderItem(true, (date + 1).toString(), lunar, isCurrentDay(calendar), isHoliday)
        }
        /**
         * 显示下个月的日期
         */
        val nextDate = position - getCurrentMonthTotalDay()
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val nextMonth = if (month == 12) 1 else month + 1
        val nextYear = if (month == 12) year + 1 else year
        calendar.set(Calendar.YEAR, nextYear)
        calendar.set(Calendar.MONTH, nextMonth)
        calendar.set(Calendar.DAY_OF_MONTH, nextDate)
        val holiday = getHolidayName(calendar)
        val isHoliday = !TextUtils.isEmpty(holiday)
        val lunar = if (isHoliday) holiday else getLunarDate(calendar)
        return CalenderItem(false, nextDate.toString(), lunar, false, isHoliday)
    }

    private fun getLunarDate(calendar: Calendar): String {
        val chineseDate = ChineseDate(Date(calendar.timeInMillis))
        val chineseDay = chineseDate.chineseDay
        if (TextUtils.equals("初一", chineseDay)) {
            return chineseDate.chineseMonthName
        }
        return chineseDay
    }

    private fun getHolidayName(calendar: Calendar): String {
        return chineseHolidayList.find {
            it.year == calendar.get(Calendar.YEAR)
                    && it.month == (calendar.get(Calendar.MONTH) + 1)
                    && it.day == calendar.get(Calendar.DATE)
        }?.holiday ?: ""
    }

    /**
     * 计算上个月有多少天，用来展示上个月从几号开始倒数
     */
    private fun getLastMonthTotalDay(): Int {
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(Calendar.YEAR)
        val thisMonth = calendar.get(Calendar.MONTH) + 1

        val year = if (thisMonth == 1) {
            thisYear - 1
        } else {
            thisYear
        }
        val month = if (thisMonth == 1) {
            12
        } else {
            thisMonth
        }
        return DateUtil.getDayCountWithMonth(year, month)
    }

    /**
     * 获取当前月份总共多少天
     */
    private fun getCurrentMonthTotalDay(): Int {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        return DateUtil.getDayCountWithMonth(year, month)
    }

    /**
     * 获取当月份第一天是周几
     * 也就是当前月份开始的第一天，需要从第几个位置开始往后排列
     * 周日为第0天
     * 一行最多显示七天
     */
    private fun getCurrentMonthStartWeek(): Int {
        val calendar = Calendar.getInstance()
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
     * 是否是当天
     */
    private fun isCurrentDay(that: Calendar): Boolean {
        val calendar = Calendar.getInstance()
        return that.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && that.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && that.get(Calendar.DATE) == calendar.get(Calendar.DATE)
    }

}