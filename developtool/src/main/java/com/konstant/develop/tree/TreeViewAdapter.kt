package com.konstant.develop.tree

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.konstant.develop.R
import com.konstant.develop.getScreenWidth
import com.konstant.develop.utils.SizeUtil
import com.yangcong345.context_provider.ContextProvider

/**
 * 时间：2022/5/16 17:32
 * 作者：吕卡
 * 备注：知识图谱的适配器
 */

class AdapterTreeView : TreeViewAdapterInterface {

    private val nodeList = mutableListOf<TreeNode>()
    private val arrowList = mutableListOf<TreeArrow>()
    private var mItemClickListener: ((Int) -> Unit)? = null
    private var mLastLearnTopicId = ""

    private val sizeUtil = SizeUtil(ContextProvider.getApplication())

    fun setLastLearnTopic(topicId: String) {
        mLastLearnTopicId = topicId
    }

    /**
     * 外部给它添加数据
     */
    fun setData() {
        nodeList.add(TreeNode(0, 1))
        nodeList.add(TreeNode(1, 0))
        nodeList.add(TreeNode(1, 1))
        nodeList.add(TreeNode(1, 2))
        nodeList.add(TreeNode(2, 0))
        nodeList.add(TreeNode(2, 1))
        nodeList.add(TreeNode(2, 2))

        arrowList.add(TreeArrow(0, 1, 1, 1))
        arrowList.add(TreeArrow(1, 0, 1, 1))
        arrowList.add(TreeArrow(1, 2, 1, 1))

        arrowList.add(TreeArrow(1, 1, 2, 2))
        arrowList.add(TreeArrow(1, 1, 2, 1))
        arrowList.add(TreeArrow(1, 1, 2, 0))

    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        mItemClickListener = listener
    }

    override fun getNodeList(): List<TreeNode> {
        return nodeList
    }

    override fun getArrowList(): List<TreeArrow> {
        return arrowList
    }

    override fun onCreateView(context: Context, parent: ViewGroup, index: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chapter_tree_view, parent, false)
        sizeUtil.resetView(view)
        view.setOnClickListener {
            if (!interceptClick()) {
                mItemClickListener?.invoke(index)
            }
        }
        return view
    }

    override fun getArrowColor(): Int {
        return Color.parseColor("#17305A")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getArrowBitmap(context: Context): Bitmap {
        val drawable = context.resources.getDrawable(R.drawable.bitmap_jiantou_black)
        val bitmap = Bitmap.createBitmap(sizeUtil.resetValue(9), sizeUtil.resetValue(9), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun getHorizontalStep(context: Context): Float {
        return sizeUtil.resetValue(263f)
    }

    override fun getVerticalStep(context: Context): Float {
        return sizeUtil.resetValue(15f)
    }

    override fun getTopPadding(): Float {
        return sizeUtil.resetValue(78f)
    }

    override fun getBottomPadding(): Float {
        return sizeUtil.resetValue(11f)
    }

    override fun getLeftPadding(): Float {
        return sizeUtil.resetValue(56f)
    }

    override fun getRightPadding(): Float {
        return sizeUtil.resetValue(45f)
    }

    /**
     * 因为每个item的大小是根据屏幕的宽度百分比计算出来的
     * 因此这里单独计算图片半高时，也需要用百分比来计算
     */
    override fun getAlignHeight(context: Context): Float {
        val width = context.getScreenWidth().toFloat() / 900 * 184f
        return width / 184f * 110f / 2
    }

    /**
     * 标签的尺寸，也是需要按照每个卡片的百分比进行计算
     */
    override fun getTagHeight(context: Context): Float {
        val width = context.getScreenWidth().toFloat() / 900 * 184f
        return width / 184f * 40f
    }

    private var lastClickTime = 0L
    private fun interceptClick(): Boolean {
        val timeStamp = System.currentTimeMillis()
        if (timeStamp - lastClickTime < 300) return true
        lastClickTime = timeStamp
        return false
    }

}