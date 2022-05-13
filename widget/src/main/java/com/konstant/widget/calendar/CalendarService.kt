package com.konstant.widget.calendar

import android.content.Intent
import android.widget.RemoteViewsService

class CalendarService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CalendarViewFactory(this, intent)
    }
}