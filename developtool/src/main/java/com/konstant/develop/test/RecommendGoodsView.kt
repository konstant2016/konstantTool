package com.konstant.develop.test

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.konstant.develop.R
import com.konstant.develop.gone
import com.konstant.develop.visible
import kotlinx.android.synthetic.main.layout_recommend_goods_view.view.*

/**
 * 时间：2021/12/6 2:59 下午
 * 作者：吕卡
 * 备注：推荐商品的控件
 */

class RecommendGoodsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    private val mGoodsList = mutableListOf<RevenueScrollVideoGoods>()
    private var mExpand = false
    private var mVideoTitle = ""
    private var mCurrentOrientation = Configuration.ORIENTATION_PORTRAIT

    init {
        View.inflate(context, R.layout.layout_recommend_goods_view, this)
        initBaseViews()
    }

    private fun initBaseViews() {
        layout_recommend.gone()
        img_box.setOnClickListener {
            showGoodsListDialog()
        }
        showIconShakeAnimation()
        updateOrientation()
    }

    /**
     * 设置视频标题
     * 标题展开时收起推荐商品
     */
    fun setVideoTitle(title: String, describe: String) {
        mVideoTitle = title
        video_describe.text = describe
        video_title.setOnClickListener {
            mExpand = !mExpand
            updateVideoTitle()
        }
        updateVideoTitle()
    }

    /**
     * 设置商品列表
     */
    fun setRecommendList(goodsList: List<RevenueScrollVideoGoods>) {
        mGoodsList.clear()
        mGoodsList.addAll(goodsList)
        updateGoodsBox()
    }

    /**
     * 显示第一个推荐商品
     * 显示推荐商品时收起标题
     */
    fun showRecommendGoods() {
        val data = mGoodsList.firstOrNull() ?: return

        tv_title.text = data.name ?: ""
        tv_price.text = "￥${data.baseValue}"
        img_close.setOnClickListener {
            layout_recommend.gone()
        }
        layout_recommend.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bottom_right_to_center))
        layout_recommend.visible()
        mExpand = false
        updateVideoTitle()
    }

    /**
     * 设置当前的屏幕方向
     */
    fun setCurrentOrientation(orientation: Int) {
        mCurrentOrientation = orientation
        updateOrientation()
    }

    private fun showGoodsListDialog() {
        val innerContext = context
        if (innerContext is FragmentActivity) {
            RecommendDialog.showDialog(innerContext.supportFragmentManager, mGoodsList)
        }
    }

    /**
     * 角标抖动效果
     */
    private fun showIconShakeAnimation() {
        tv_count.post {
            val t1 = Keyframe.ofFloat(0f, 0f)
            val t2 = Keyframe.ofFloat(0.2f, 0f)
            val t3 = Keyframe.ofFloat(0.45f, -10f)
            val t4 = Keyframe.ofFloat(0.7f, 0f)
            val t5 = Keyframe.ofFloat(0.85f, 5f)
            val t6 = Keyframe.ofFloat(1.0f, 0f)
            tv_count.pivotX = 0f
            tv_count.pivotY = tv_count.height.toFloat()
            val holder = PropertyValuesHolder.ofKeyframe(View.ROTATION, t1, t2, t3, t4, t5, t6)
            val animate = ObjectAnimator.ofPropertyValuesHolder(tv_count, holder)
            animate.duration = 1000
            animate.repeatCount = -1
            animate.start()
        }
    }

    private fun updateVideoTitle() {
        video_title.post {
            if (mExpand) {
                layout_recommend.gone()
                video_title.text = mVideoTitle
                video_title.maxLines = 100
                return@post
            }
            video_title.maxLines = 2
            val width = video_title.width
            val singleNumber = (width / video_title.textSize).toInt()
            val totalNumber = mVideoTitle.length
            if (totalNumber <= singleNumber * 2) {
                video_title.text = mVideoTitle
                return@post
            }
            val showText = mVideoTitle.substring(0, singleNumber * 2 - 1) + "..."
            video_title.text = showText
        }
    }

    private fun updateOrientation(){
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT){
            video_title.visible()
            video_describe.visible()
        }else{
            video_title.gone()
            video_describe.gone()
        }
    }

    private fun updateGoodsBox() {
        if (mGoodsList.isEmpty()) {
            img_box.gone()
            tv_count.gone()
            return
        }
        img_box.visible()
        tv_count.visible()
        tv_count.text = "${mGoodsList.size}"
    }

    /**
     * 当前是否为横屏
     */
    private fun isScreenLandscape(): Boolean {
        return context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}