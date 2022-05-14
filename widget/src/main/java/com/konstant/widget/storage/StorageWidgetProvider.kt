package com.konstant.widget.storage

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.StatFs
import android.provider.AlarmClock
import android.provider.Settings
import android.text.format.Formatter
import android.widget.RemoteViews
import com.konstant.widget.R
import com.konstant.widget.calendar.CalendarWidgetProvider
import com.konstant.widget.time.TimeWidgetProvider

/**
 * 时间：2022/5/13 22:52
 * 作者：吕卡
 * 备注：桌面可用内存部件
 */

class StorageWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateStorage(context)
    }

    companion object {

        fun isEnabled(context: Context): Boolean {
            val storageName = ComponentName(context, StorageWidgetProvider::class.java)
            val storageWidget = AppWidgetManager.getInstance(context).getAppWidgetIds(storageName)
            return storageWidget.isNotEmpty()
        }

        fun updateStorage(context: Context) {
            if (!isEnabled(context)) return
            val remoteView = RemoteViews(context.packageName, R.layout.storage_widget)
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            val availableBlocks = stat.availableBlocksLong

            val intent = Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            remoteView.setOnClickPendingIntent(R.id.view_parent, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE))

            val totalSize = totalBlocks * blockSize
            val availableSize = availableBlocks * blockSize
            val usedSize = totalSize - availableSize
            val percent = ((totalSize - availableSize).toFloat() / totalSize * 100).toInt()
            val totalString = Formatter.formatFileSize(context, totalSize)
            val usedString = Formatter.formatFileSize(context, usedSize)
            val describe = "已用$usedString / 共计$totalString"

            if (percent == getLastPercent(context)) return
            updateLastPercent(context, percent)

            remoteView.setTextViewText(R.id.tv_percent, "$percent")
            remoteView.setTextViewText(R.id.tv_describe, describe)
            val component = ComponentName(context, StorageWidgetProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
        }

        private fun getLastPercent(context: Context): Int {
            val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
            return sp.getInt("LastPercent", 0)
        }

        private fun updateLastPercent(context: Context, percent: Int) {
            val sp = context.getSharedPreferences("Widget", Context.MODE_PRIVATE)
            sp.edit().putInt("LastPercent", percent)
        }

    }

}