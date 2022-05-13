package com.konstant.tool.lite.widget.calendar

import android.text.TextUtils
import androidx.annotation.Keep
import cn.hutool.core.date.ChineseDate
import cn.hutool.core.date.chinese.LunarFestival
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
        val currentMonth: Boolean,      // 类型：周，上个月，本月
        val publicDate: String,         // 日期
        val chineseDate: String,        // 农历
        val currentDay: Boolean,        // 是否是当天
        val isHoliday: Boolean          // 是否是节气或者假期
    )

    /**
     * 第一行显示的周排列
     * 第二行开始显示日期
     */
    fun getStringWithPosition(position: Int, calendar: Calendar): CalenderItem {
        /**
         * 计算第一行，开始的位置，这个地方显示的是上个月的最后几天
         */
        if (position < getCurrentMonthStartWeek(calendar)) {
            val date = getLastMonthDayWithPosition(position, calendar)
            val month = calendar.get(Calendar.MONTH)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date)
            return buildCalendarItem(calendar, false)
        }
        /**
         * 显示本月的具体数据
         */
        val date = position - getCurrentMonthStartWeek(calendar)
        if (date <= getCurrentMonthTotalDay(calendar)) {
            calendar.set(Calendar.DAY_OF_MONTH, date + 1)
            return buildCalendarItem(calendar, true)
        }
        /**
         * 显示下个月的日期
         */
        val nextDate = position - getCurrentMonthTotalDay(calendar)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val nextMonth = if (month == 12) 1 else month + 1
        val nextYear = if (month == 12) year + 1 else year
        calendar.set(Calendar.YEAR, nextYear)
        calendar.set(Calendar.MONTH, nextMonth)
        calendar.set(Calendar.DAY_OF_MONTH, nextDate)
        return buildCalendarItem(calendar, false)
    }

    /**
     * 根据传入的时间来转换为我们需要时参数
     */
    private fun buildCalendarItem(calendar: Calendar, isCurrentMonth: Boolean): CalenderItem {
        val holiday = getHolidayName(calendar)
        val isHoliday = !TextUtils.isEmpty(holiday)
        val lunar = getHolidayName(calendar) ?: getLunarDate(calendar)
        val date = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val isCurrentDay = isCurrentDay(calendar)
        return CalenderItem(isCurrentMonth, date, lunar, isCurrentDay, isHoliday)
    }

    private fun getLunarDate(calendar: Calendar): String {
        val chineseDate = ChineseDate(Date(calendar.timeInMillis))
        // 获取节日
        val festivals = chineseDate.festivals
        if (!TextUtils.isEmpty(festivals)) return festivals
        // 获取节气
        val term = chineseDate.term
        if (!TextUtils.isEmpty(term)) return term
        // 获取农历日
        val chineseDay = chineseDate.chineseDay
        if (TextUtils.equals("初一", chineseDay)) {
            return chineseDate.chineseMonthName
        }
        return chineseDay
    }

    private fun getHolidayName(calendar: Calendar): String? {
        return chineseHolidayList.find {
            it.month == (calendar.get(Calendar.MONTH) + 1)
                    && it.day == calendar.get(Calendar.DATE)
        }?.holiday
    }

    /**
     * 计算上个月有多少天，用来展示上个月从几号开始倒数
     */
    private fun getLastMonthTotalDay(calendar: Calendar): Int {
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
    private fun getCurrentMonthTotalDay(calendar: Calendar): Int {
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
    private fun getCurrentMonthStartWeek(calendar: Calendar): Int {
        calendar.set(Calendar.DATE, 1)
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    /**
     * 计算指定位置，如果是显示上个月的日期的话，那么应该显示几号
     * 先算出日历左上角的日期：用上个月的总天数 - 本月第一天的的 day of week
     * 再根据position，计算出应该显示天
     */
    private fun getLastMonthDayWithPosition(position: Int, calendar: Calendar): Int {
        val startDay = getLastMonthTotalDay(calendar) - getCurrentMonthStartWeek(calendar)
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