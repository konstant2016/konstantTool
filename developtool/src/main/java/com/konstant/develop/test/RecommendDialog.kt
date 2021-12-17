package com.konstant.develop.test

import AdapterRecommendGoodsDialog
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.konstant.develop.R
import com.konstant.develop.dp2px
import com.konstant.develop.getScreenHeight
import com.konstant.develop.getScreenWidth
import kotlinx.android.synthetic.main.layout_dialog_recommend_goods.*

/**
 * 时间：2021/12/7 2:19 下午
 * 作者：吕卡
 * 备注：推荐商品的dialog
 */

class RecommendDialog : DialogFragment() {

    private val mGoodsList = mutableListOf<RevenueScrollVideoGoods>()

    companion object {
        private const val TAG = "RecommendDialog"
        fun showDialog(fragmentManager: FragmentManager, goodsList: List<RevenueScrollVideoGoods>) {
            val dialog = RecommendDialog()
            dialog.mGoodsList.clear()
            dialog.mGoodsList.addAll(goodsList)
            dialog.show(fragmentManager, TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isScreenLandscape()) {
            val screenWidth = context?.getScreenWidth() ?: 0
            val width = screenWidth * 0.6
            dialog?.window?.setLayout(width.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
            dialog?.window?.setGravity(Gravity.END)
        } else {
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.layout_dialog_recommend_goods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        super.onViewCreated(view, savedInstanceState)
        initBaseViews()
    }

    private fun initBaseViews() {
        resetRecyclerViewHeight()
        recycler_view_dialog.adapter = AdapterRecommendGoodsDialog(mGoodsList)
        img_close.setOnClickListener { dismissAllowingStateLoss() }
        layout_dialog_bg.setOnClickListener { dismissAllowingStateLoss() }
        if (isScreenLandscape()) {
            img_bg_dialog.setImageResource(R.drawable.bg_white_left_radius_8)
            right_space.layoutParams.width = getNavigationBarHeight()
        } else {
            img_bg_dialog.setImageResource(R.drawable.bg_white_top_radius_8)
            bottom_space.layoutParams.height = getNavigationBarHeight()
        }
    }

    /**
     * 横屏时，判断recyclerview的高度加上标题图片的高度，如果超出屏幕，则需要手动控制recyclerview的高度，否则标题图片会被顶出屏幕外
     * 竖屏同理
     */
    private fun resetRecyclerViewHeight() {
        val innerContext = context ?: return
        val screenHeight = innerContext.getScreenHeight()
        val titleHeight = innerContext.dp2px(64f)
        val recyclerViewHeight = innerContext.dp2px(116f) * mGoodsList.size
        if (isScreenLandscape()) {
            if (titleHeight + recyclerViewHeight > screenHeight) {
                recycler_view_dialog.layoutParams.height = screenHeight - titleHeight
            } else {
                recycler_view_dialog.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        } else {
            if (titleHeight + recyclerViewHeight > screenHeight * 0.7) {
                recycler_view_dialog.layoutParams.height = (screenHeight * 0.7).toInt() - titleHeight
            } else {
                recycler_view_dialog.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
    }

    /**
     * 当前是否为横屏
     */
    private fun isScreenLandscape(): Boolean {
        return context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 因为约束布局的原因，高度为0会导致整个UI均分，因此这里返回的最低值为1像素
     */
    private fun getNavigationBarHeight(): Int {
        val innerContext = context
        if (innerContext !is Activity) {
            return 1
        }
        val display = innerContext.window.windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)
        val decorView = innerContext.window.decorView
        val conf = innerContext.getResources().configuration
        val height = if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            val contentView = decorView.findViewById<View>(android.R.id.content)
            Math.abs(point.x - contentView.width)
        } else {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            Math.abs(rect.bottom - point.y)
        }
        if (height <= 0) return 1
        return height
    }

}