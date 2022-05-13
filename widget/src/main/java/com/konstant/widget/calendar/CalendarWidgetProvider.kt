package com.konstant.widget.calendar

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.provider.Settings
import android.util.Log
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

    /**
     * update
     */
    private fun updateCalendarWidget(context: Context) {
        val remoteView = RemoteViews(context.packageName, R.layout.calendar_widget)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val string = "$year 年 ${month + 1} 月"
        remoteView.setTextViewText(R.id.tv_current_month, string)
        val intent = Intent(context, CalendarService::class.java)
        remoteView.setRemoteAdapter(R.id.grid_view, intent)
        val component = ComponentName(context, CalendarWidgetProvider::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
    }

}