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

    private var textView: TextView by Delegates.notNull()
    private var imageView: ImageView by Delegates.notNull()

    private var selectedImg: Drawable? = null
    private var unSelectedImg: Drawable? = null
    private var title = ""

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_drawable_text_view, this, true)
        textView = view.findViewById(R.id.btn_title)
        imageView = view.findViewById(R.id.btn_img)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        selectedImg = attr.getDrawable(R.styleable.DrawableTextView_selectedDrawable)
        unSelectedImg = attr.getDrawable(R.styleable.DrawableTextView_unSelectedDrawable)
        title = attr.getString(R.styleable.DrawableTextView_selectedText) ?: ""
        attr.recycle()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        textView.setText(title)
        if (selected) {
            imageView?.setImageDrawable(selectedImg!!)
        } else {
            imageView?.setImageDrawable(unSelectedImg!!)
        }
    }


}