package com.konstant.widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}