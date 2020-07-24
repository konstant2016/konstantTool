package com.konstant.tool.lite.module.skip

import android.graphics.drawable.Drawable

data class AppDataWrapper(var isChecked: Boolean,
                          val appName: String,
                          val packageName: String,
                          val icon: Drawable)