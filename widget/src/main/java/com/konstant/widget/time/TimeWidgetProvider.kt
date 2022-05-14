package com.konstant.widget.time

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.RemoteViews
import com.konstant.widget.R
import com.konstant.widget.calendar.CalendarWidgetProvider
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class TimeWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateTimeWidget(context)
    }

    companion object {

        fun isEnabled(context: Context): Boolean {
            val timeName = ComponentName(context, TimeWidgetProvider::class.java)
            val timeWidget = AppWidgetManager.getInstance(context).getAppWidgetIds(timeName)
            return timeWidget.isNotEmpty()
        }

        fun updateTimeWidget(context: Context) {
            if (!isEnabled(context)) return
            val remoteView = RemoteViews(context.packageName, R.layout.time_widget)
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val minuteString = if (minute > 9) minute.toString() else "0$minute"
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
                in 1..2 -> "丑时"
                in 3..4 -> "寅时"
                in 5..6 -> "卯时"
                in 7..8 -> "辰时"
                in 9..10 -> "巳时"
                in 11..12 -> "午时"
                in 13..14 -> "未时"
                in 15..16 -> "申时"
                in 17..18 -> "酉时"
                in 19..20 -> "戌时"
                in 21..22 -> "亥时"
                else -> "子时"
            }
            remoteView.setTextViewText(R.id.tv_time, "${hour}:$minuteString")
            remoteView.setTextViewText(R.id.tv_date, "${weekString}/${period}")
            val component = ComponentName(context, TimeWidgetProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
        }
    }

}
