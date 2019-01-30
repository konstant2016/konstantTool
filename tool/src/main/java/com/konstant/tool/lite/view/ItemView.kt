package com.konstant.tool.lite.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.konstant.tool.lite.R

class ItemView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAtr: Int = 0) : RelativeLayout(context, attr, defStyleAtr) {

    init {
        isClickable = true
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_main, null)
        val array = context.obtainStyledAttributes(attr, R.styleable.ItemView)
        val string = array.getString(R.styleable.ItemView_text)
        val text = view.findViewById(R.id.tv_item) as TextView
        text.text = string
        addView(view)
        array.recycle()
    }

}