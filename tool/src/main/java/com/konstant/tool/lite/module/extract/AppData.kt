package com.konstant.tool.lite.module.extract

import android.graphics.drawable.Drawable
import java.text.Collator
import java.util.*

data class AppData(val packageName: String, val icon: Drawable, val appName: String) : Comparable<AppData> {
    override fun compareTo(other: AppData): Int {
        return Collator.getInstance(Locale.CHINA).compare(appName, other.appName)
    }
}
