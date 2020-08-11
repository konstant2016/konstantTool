package com.konstant.tool.lite.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.item_layout_main.view.*

class ItemView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAtr: Int = 0)
    : RelativeLayout(context, attr, defStyleAtr) {

    private var mOnClickListener: OnClickListener? = null
    private var mCheckedChangeListener: ((isChecked: Boolean) -> Unit)? = null

    private val mView: View = LayoutInflater.from(context).inflate(R.layout.item_layout_main, null)

    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.ItemView)
        val title = array.getString(R.styleable.ItemView_title)
        val subTitle = array.getString(R.styleable.ItemView_subTitle)
        val hintState = array.getBoolean(R.styleable.ItemView_showHint, false)
        val moreState = array.getBoolean(R.styleable.ItemView_showMore, true)
        val showSwitch = array.getBoolean(R.styleable.ItemView_showSwitch, false)
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
            if (showSwitch) {
                item_img.visibility = View.GONE
                item_hint.visibility = View.GONE
                switch_view.visibility = View.VISIBLE
                switch_view.setOnCheckedChangeListener { _, isChecked -> mCheckedChangeListener?.invoke(isChecked) }
                setOnClickListener { switch_view.isChecked = !switch_view.isChecked }
            } else {
                item_img.visibility = View.VISIBLE
                item_hint.visibility = View.VISIBLE
                switch_view.visibility = View.GONE
            }
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

    fun setChecked(checked: Boolean) {
        mView.switch_view.isChecked = checked
    }

    fun setOnCheckedChangeListener(checkedChangeListener: (isChecked: Boolean) -> Unit) {
        mCheckedChangeListener = checkedChangeListener
    }

}
