package com.konstant.tool.lite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.layout_pop_konstant.view.*

/**
* 作者：konstant
* 时间：2019/11/7 17:47
* 描述：从上往下弹的那个弹窗
*/

class KonstantPopupWindow(context: Context) : PopupWindow(
        LayoutInflater.from(context).inflate(R.layout.layout_pop_konstant, null),
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        true) {

    private var mView: View? = null           // 内部填充的布局
    private var mOnItemClickListener: ((position: Int) -> Unit)? = null   // 列表的点击回调
    private val mList = ArrayList<String>()  // 内部填充的列表

    // 设置内部控件
    fun addView(view: View): KonstantPopupWindow {
        mView = view
        return this
    }

    // 设置列表点击监听
    fun setOnItemClickListener(listener: (position: Int) -> Unit): KonstantPopupWindow {
        mOnItemClickListener = listener
        return this
    }

    // 设置填充列表
    fun setItemList(stringList: List<String>): KonstantPopupWindow {
        mList.clear()
        mList.addAll(stringList)
        return this
    }

    override fun showAsDropDown(anchor: View) {
        if (mList.isNotEmpty()) {
            contentView.view_list.apply {
                visibility = View.VISIBLE
                adapter = Adapter(context, mList)
                setOnItemClickListener { _, view, position, _ ->
                    dismiss()
                    mOnItemClickListener?.invoke(position)
                }
            }
        }
        if (mView != null) {
            contentView.layout_content.removeAllViews()
            contentView.layout_content.addView(mView)
        }
        super.showAsDropDown(anchor)
    }

    override fun dismiss() {
        contentView.layout_content.removeAllViews()
        super.dismiss()
    }
}