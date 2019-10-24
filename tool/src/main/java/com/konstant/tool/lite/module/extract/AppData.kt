package com.konstant.tool.lite.module.extract

import android.graphics.drawable.Drawable
import androidx.annotation.Keep

data class AppData(val packageName: String, val icon: Drawable, val appName: String) : Comparable<AppData> {
    override fun compareTo(other: AppData): Int {
        return appName.compareTo(other.appName)
    }
}
