package com.konstant.tool.lite.widget.calendar

import android.content.Intent
import android.widget.RemoteViewsService

class CalendarService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CalendarViewFactory(this, intent)
    }
}