package com.konstant.tool.lite.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.konstant.tool.lite.R
import java.util.*

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
        updateAppWidget(context, appWidgetManager, appWidgetIds[0])
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
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
}