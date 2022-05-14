package com.konstant.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.konstant.widget.calendar.CalendarService
import com.konstant.widget.calendar.CalendarWidgetProvider
import com.konstant.widget.storage.StorageWidgetProvider
import com.konstant.widget.time.TimeWidgetProvider

/**
 * 时间：2022/5/13 23:43
 * 作者：吕卡
 * 备注：用来处理组件的刷新逻辑，应用启动后，启动这个服务，然后接收时间的广播，用来刷新页面数据
 */

class WidgetForegroundService : Service() {

    companion object {
        fun startForegroundService(context: Context) {
            val intent = Intent(context, WidgetForegroundService::class.java)
            context.startService(intent)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateWidget(context, false)
        }
    }

    private fun updateWidget(context: Context, forceRefresh: Boolean) {
        CalendarWidgetProvider.updateCalendarWidget(context, forceRefresh)
        StorageWidgetProvider.updateStorage(context)
        TimeWidgetProvider.updateTimeWidget(context)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        updateWidget(this, true)
        sendNotification()
        val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
        registerReceiver(receiver, intentFilter)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun sendNotification() {
        val notification = NotificationCreator.createForegroundNotification(this)
        startForeground(1, notification)
    }
}