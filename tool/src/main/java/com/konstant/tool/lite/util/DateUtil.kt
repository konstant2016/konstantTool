package com.konstant.tool.lite.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@SuppressLint("SimpleDateFormat")
object DateUtil {

    val FORMAT = "yyyy年MM月dd日"

    // 获取当前日期
    fun getCurrentDate(format: String) = SimpleDateFormat(format).format(Date())

    // 获取指定年
    fun getYearWithDate(date: String): String {
        val parse = SimpleDateFormat(FORMAT).parse(date)
        return SimpleDateFormat("yyyy").format(parse)
    }

    // 获取指定月
    fun getMonthWithDate(date: String): String {
        val parse = SimpleDateFormat(FORMAT).parse(date)
        return SimpleDateFormat("MM").format(parse)
    }

    // 获取指定日
    fun getDayWithDate(date: String): String {
        val parse = SimpleDateFormat(FORMAT).parse(date)
        return SimpleDateFormat("dd").format(parse)
    }

    // 计算两个日期的间隔天数
    fun calculateDateInterval(start: String, end: String): Int {
        val startMill = SimpleDateFormat(FORMAT).parse(start).time
        val endMill = SimpleDateFormat(FORMAT).parse(end).time
        return (abs(startMill - endMill) / 1000 / 3600 / 24).toInt()
    }

    // 计算指定日期往前、往后的天数
    fun calculateLaterDate(date: String, days: Int): String {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat(FORMAT)
        calendar.timeInMillis = format.parse(date).time
        calendar.add(Calendar.DATE, days)
        return format.format(calendar.time)
    }

}