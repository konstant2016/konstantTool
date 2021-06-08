package com.konstant.tool.lite.base

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue

fun Context.getThemColor(resId: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.isScreenHorizontal():Boolean{
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}