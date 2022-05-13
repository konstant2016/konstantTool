package com.konstant.tool.lite.widget.calendar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.konstant.tool.lite.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

/**
 * Implementation of App Widget functionality.
 */
class CalendarWidgetProvider : AppWidgetProvider() {

    /**
     * 更新此应用中的所有组件
     * appWidgetIds：有多个组件需要更新，这里传的是组件列表
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        updateCalendarWidget(context, appWidgetManager, appWidgetIds[0])
//        updateTimeWidget(context, appWidgetManager, appWidgetIds[1])
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * update
     */
    private fun updateCalendarWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val remoteView = RemoteViews(context.packageName, R.layout.calendar_widget)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val string = "$year 年 ${month + 1} 月"
        remoteView.setTextViewText(R.id.tv_current_month, string)
        val intent = Intent(context, CalendarService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        remoteView.setRemoteAdapter(R.id.grid_view, intent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteView)
    }

    /**
     *
     */
    private fun updateTimeWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val remoteView = RemoteViews(context.packageName, R.layout.time_widget)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val week = calendar.get(Calendar.DAY_OF_WEEK)
        val weekString = when (week) {
            Calendar.SUNDAY -> "周日"
            Calendar.MONDAY -> "周一"
            Calendar.TUESDAY -> "周二"
            Calendar.WEDNESDAY -> "周三"
            Calendar.THURSDAY -> "周四"
            Calendar.FRIDAY -> "周五"
            Calendar.SATURDAY -> "周六"
            else -> ""
        }

        val period = when (hour) {
            in 3..5 -> "凌晨"
            in 0..3 -> "凌晨"
            else->""
        }
    }
}