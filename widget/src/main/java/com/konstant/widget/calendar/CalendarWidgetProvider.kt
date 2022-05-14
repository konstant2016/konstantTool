package com.konstant.widget.calendar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
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

    companion object {

        fun isEnabled(context: Context): Boolean {
            val calendarName = ComponentName(context, CalendarWidgetProvider::class.java)
            val calendarWidget = AppWidgetManager.getInstance(context).getAppWidgetIds(calendarName)
            return calendarWidget.isNotEmpty()
        }

        /**
         * 先判断组件是否已添加到桌面，如果没有添加，则中断执行
         * 再判断当前日期与上次刷新的日期是否相同，如果相同，则无需刷新，中断执行
         * 保存上次刷新的日期，执行刷新操作
         */
        fun updateCalendarWidget(context: Context, forceRefresh: Boolean = false) {
            if (!isEnabled(context)) return

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val string = "$year 年 ${month + 1} 月"

            val needRefresh = hour == 0 && minute < 5
            if (!needRefresh && !forceRefresh) return

            val remoteView = RemoteViews(context.packageName, R.layout.calendar_widget)
            remoteView.setTextViewText(R.id.tv_current_month, string)
            val intent = Intent(context, CalendarService::class.java)
            // 解决日历部件不刷新，https://code-examples.net/zh-CN/q/dd0d68
            intent.data = Uri.fromParts(ContentResolver.SCHEME_CONTENT, Math.random().toString(), null)
            remoteView.setRemoteAdapter(R.id.grid_view, intent)
            val component = ComponentName(context, CalendarWidgetProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
        }
    }

}