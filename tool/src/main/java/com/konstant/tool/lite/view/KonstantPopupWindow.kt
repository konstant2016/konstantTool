package com.konstant.tool.lite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.layout_pop_konstant.view.*

/**
 * 作者：konstant
 * 时间：2019/11/7 17:47
 * 描述：从上往下弹的那个弹窗
 */

class KonstantPopupWindow(private val context: Context) : PopupWindow(
        LayoutInflater.from(context).inflate(R.layout.layout_pop_konstant, null),
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
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
        contentView.pop_root.setOnClickListener { dismiss() }
        setViewHeight(anchor)
        super.showAsDropDown(anchor)
    }

    /**
     * 计算出顶部需要留出多少高度，这个高度不需要设置半透明的效果
     */
    private fun setViewHeight(anchor: View) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else (context.resources.displayMetrics.density * 25).toInt()
        val position = IntArray(2)
        anchor.getLocationOnScreen(position)
        // view上边缘距离屏幕顶部的高度 + view本身的高度 - 状态栏的高度，最终得到popupWindow距离屏幕顶部的高度
        val titleHeight = position[1] + anchor.measuredHeight - statusBarHeight
        contentView.view_title_space.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, titleHeight)
    }

    override fun dismiss() {
        contentView.layout_content.removeAllViews()
        super.dismiss()
    }
}