package com.konstant.develop.view

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_multi_text_view.view.*

class MultiSelectTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {

    private var mText = ""
    private var mTextSize = 12f

    init {
        View.inflate(context, R.layout.layout_multi_text_view, this)
    }

    fun setText(text: String, textSize: Float) {
        mText = text
        mTextSize = textSize
        updateTextStatus()
    }

    private fun updateTextStatus() {
        this.post {
            if (isSelected) {
                val width = this.measuredWidth
                val singleNumber = getLineMaxNumber(mText, tv_1.paint, width)
                val textWidth = tv_1.paint.measureText(mText)
                if (textWidth > width) {
                    val text1 = mText.substring(0, singleNumber)
                    val text2 = mText.replace(text1, "")
                    tv_1.text = text1
                    tv_2.text = text2
                    view_bg_1.visibility = View.VISIBLE
                    view_bg_2.visibility = View.VISIBLE
                    tv_2.visibility = View.VISIBLE
                } else {
                    tv_1.text = mText
                    view_bg_1.visibility = View.VISIBLE
                    view_bg_2.visibility = View.GONE
                    tv_2.visibility = View.GONE
                }
            } else {
                view_bg_2.visibility = View.GONE
                tv_2.visibility = View.GONE
                view_bg_1.visibility = View.GONE
                tv_1.text = mText
            }
        }
        tv_1.textSize = mTextSize
        tv_2.textSize = mTextSize
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        updateTextStatus()
        requestLayout()
    }

    /**
     * 获取textview一行最大能显示几个字(需要在TextView测量完成之后)
     *
     * @param text     文本内容
     * @param paint    textview.getPaint()
     * @param maxWidth textview.getMaxWidth()/或者是指定的数值,如200dp
     */
    private fun getLineMaxNumber(text: String?, paint: TextPaint, maxWidth: Int): Int {
        if (null == text || "" == text) {
            return 0
        }
        val staticLayout = StaticLayout(text, paint, maxWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false)
        //获取第一行最后显示的字符下标
        return staticLayout.getLineEnd(0)
    }
}