package com.konstant.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.konstant.widget.calendar.CalendarWidgetProvider
import com.konstant.widget.storage.StorageWidgetProvider
import com.konstant.widget.time.TimeWidgetProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!CalendarWidgetProvider.isEnabled(this)
            && !StorageWidgetProvider.isEnabled(this)
            && !TimeWidgetProvider.isEnabled(this)
        ) {
            Toast.makeText(this, "请先添加桌面部件", Toast.LENGTH_LONG).show()
        } else {
            WidgetForegroundService.startForegroundService(this)
            Toast.makeText(this, "桌面部件已刷新", Toast.LENGTH_LONG).show()
        }
        finish()
    }

    /**
     * 判断是否在桌面上添加了小部件
     */
    private fun isHasWidgetOnLauncher(): Boolean {
        val calendarName = ComponentName(this, CalendarWidgetProvider::class.java)
        val calendarWidget = AppWidgetManager.getInstance(this).getAppWidgetIds(calendarName)

        val timeName = ComponentName(this, TimeWidgetProvider::class.java)
        val timeWidget = AppWidgetManager.getInstance(this).getAppWidgetIds(timeName)

        val storageName = ComponentName(this, StorageWidgetProvider::class.java)
        val storageWidget = AppWidgetManager.getInstance(this).getAppWidgetIds(storageName)

        return calendarWidget.size + timeWidget.size + storageWidget.size > 0
    }

}