package com.konstant.tool.lite.base

import android.content.Context
import android.util.TypedValue

fun Context.getThemColor(resId: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}