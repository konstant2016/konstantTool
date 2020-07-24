package com.konstant.tool.lite.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.item_layout_main.view.*

class ItemView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAtr: Int = 0)
    : RelativeLayout(context, attr, defStyleAtr) {

    private var mOnClickListener: OnClickListener? = null

    private val mView: View = LayoutInflater.from(context).inflate(R.layout.item_layout_main, null)

    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.ItemView)
        val title = array.getString(R.styleable.ItemView_title)
        val subTitle = array.getString(R.styleable.ItemView_subTitle)
        val hintState = array.getBoolean(R.styleable.ItemView_showHint, false)
        val moreState = array.getBoolean(R.styleable.ItemView_showMore, true)
        mView.apply {
            item_title.text = title
            if (TextUtils.isEmpty(subTitle)) {
                item_sub_title.visibility = View.GONE
            } else {
                item_sub_title.visibility = View.VISIBLE
                item_sub_title.text = subTitle
            }
            item_view.setOnClickListener { mOnClickListener?.onClick(it) }
            item_hint.visibility = if (hintState) View.VISIBLE else View.GONE
            item_img.visibility = if (moreState) View.VISIBLE else View.GONE
        }
        addView(mView)
        array.recycle()
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        mOnClickListener = listener
    }

    fun setHintText(txt: String) {
        mView.item_hint.text = txt
        mView.item_hint.visibility = View.VISIBLE
    }

}
