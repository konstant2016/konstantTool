package com.konstant.tool.lite.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.R

class CalendarViewFactory(private val context: Context, private val intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {
        val type = object : TypeToken<List<DateHelper.ChineseHoliday>>() {}.type
        val json = String(context.assets.open("ChineseHoliday.json").readBytes())
        val holidayList = Gson().fromJson<List<DateHelper.ChineseHoliday>>(json, type)
        DateHelper.setChineseHoliday(holidayList)
    }

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    /**
     * 每个月最多显示6行，7列，共计42个item
     */
    override fun getCount(): Int {
        return 42
    }

    override fun getViewAt(position: Int): RemoteViews {
        val item = DateHelper.getStringWithPosition(position)
        val remoteView = RemoteViews(context.packageName, R.layout.item_calendar_widget_date)
        remoteView.setTextViewText(R.id.tv_date, item.title)
        if (item.currentMonth) {
            remoteView.setTextColor(R.id.tv_date, Color.parseColor("#CCFFFFFF"))
            remoteView.setTextColor(R.id.tv_lunar, Color.parseColor("#CCFFFFFF"))
        } else {
            remoteView.setTextColor(R.id.tv_date, Color.parseColor("#4CFFFFFF"))
            remoteView.setTextColor(R.id.tv_lunar, Color.parseColor("#4CFFFFFF"))
        }
        remoteView.setTextViewText(R.id.tv_lunar, item.subTitle)
        if (item.isHoliday) {
            remoteView.setTextColor(R.id.tv_lunar, Color.parseColor("#D9FCD8"))
        }
        if (item.currentDay) {
            remoteView.setTextColor(R.id.tv_date, Color.parseColor("#00FF00"))
            remoteView.setTextColor(R.id.tv_lunar, Color.parseColor("#00FF00"))
        }
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}