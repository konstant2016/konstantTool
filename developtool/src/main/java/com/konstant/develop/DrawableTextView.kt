package com.konstant.develop

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.properties.Delegates

class DrawableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val textView by lazy { findViewById<TextView>(R.id.btn_title) }
    private val imageView by lazy { findViewById<ImageView>(R.id.btn_img) }
    private var selectedImg: Drawable? = null
    private var unSelectedImg: Drawable? = null
    private var title = ""

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.layout_drawable_text_view, this, true)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        selectedImg = attr.getDrawable(R.styleable.DrawableTextView_selectedDrawable)
        unSelectedImg = attr.getDrawable(R.styleable.DrawableTextView_unSelectedDrawable)
        title = attr.getString(R.styleable.DrawableTextView_selectedText) ?: ""
        attr.recycle()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        textView.text = title
        if (selected) {
            selectedImg?.let {
                imageView?.setImageDrawable(it)
            }
        } else {
            unSelectedImg?.let {
                imageView?.setImageDrawable(it)
            }
        }
    }

}