package com.konstant.widget.calendar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.RemoteViews
import com.konstant.widget.R
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class CalendarWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        updateCalendarWidget(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
        sp.edit().putBoolean(KEY_CALENDAR, true).commit()
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
        sp.edit().putBoolean(KEY_CALENDAR, false).commit()
    }

    companion object {

        private const val KEY_CALENDAR = "CalendarWidget"

        fun isEnabled(context: Context): Boolean {
            val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
            return sp.getBoolean(KEY_CALENDAR, false)
        }

        /**
         * 先判断组件是否已添加到桌面，如果没有添加，则中断执行
         * 再判断当前日期与上次刷新的日期是否相同，如果相同，则无需刷新，中断执行
         * 保存上次刷新的日期，执行刷新操作
         */
        fun updateCalendarWidget(context: Context) {
            if (!isEnabled(context)) return

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val string = "$year 年 ${month + 1} 月"

            val date = "$year-$month-$day"
            if (TextUtils.equals(date, getLastUpdateDate(context))) return
            updateLastDate(context, date)

            val remoteView = RemoteViews(context.packageName, R.layout.calendar_widget)
            remoteView.setTextViewText(R.id.tv_current_month, string)
            val intent = Intent(context, CalendarService::class.java)
            remoteView.setRemoteAdapter(R.id.grid_view, intent)
            val component = ComponentName(context, CalendarWidgetProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
        }

        private fun getLastUpdateDate(context: Context): String {
            val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
            return sp.getString("LastDate", "") ?: ""
        }

        private fun updateLastDate(context: Context, date: String) {
            val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
            sp.edit().putString("LastDate", date).apply()
        }

    }

}