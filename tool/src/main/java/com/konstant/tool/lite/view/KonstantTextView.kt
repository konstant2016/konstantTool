package com.konstant.tool.lite.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * 时间：2019/5/5 11:51
 * 创建：吕卡
 * 描述：无限循环的 textView
 */

class KonstantTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    override fun isFocused() = true
}