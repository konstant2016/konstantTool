package com.konstant.develop.tree

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.develop.R
import com.konstant.develop.getScreenWidth

class AdapterTreeView : TreeViewAdapterInterface {

    private val nodeList = mutableListOf<TreeNode>()
    private val arrowList = mutableListOf<TreeArrow>()
    private var mItemClickListener: ((Int) -> Unit)? = null

    /**
     * 外部给它添加数据
     */
    fun setData() {
        nodeList.add(TreeNode(0,1))
        nodeList.add(TreeNode(1,0))
        nodeList.add(TreeNode(1,1))
        nodeList.add(TreeNode(1,2))
        nodeList.add(TreeNode(2,0))
        nodeList.add(TreeNode(2,1))
        nodeList.add(TreeNode(2,2))

        arrowList.add(TreeArrow(0,1,1,1))
        arrowList.add(TreeArrow(1,0,2,1))
        arrowList.add(TreeArrow(1,1,2,1))
        arrowList.add(TreeArrow(1,2,2,1))
        arrowList.add(TreeArrow(1,0,2,0))
        arrowList.add(TreeArrow(1,1,2,0))
        arrowList.add(TreeArrow(1,2,2,0))

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
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_tree_view, parent, false)
        val layoutParams = view.layoutParams ?: ViewGroup.LayoutParams(0, 0)
        val width = context.getScreenWidth().toFloat() / 900 * 88.5
        val height = width / 88.5 * 96
        layoutParams.height = height.toInt()
        layoutParams.width = width.toInt()
        view.setOnClickListener {
            mItemClickListener?.invoke(index)
        }
        return view
    }

    override fun getArrowColor(): Int {
        return Color.BLACK
    }

    override fun getArrowBitmap(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.icon_jiantou_black)
    }

    override fun getHorizontalStep(context: Context): Float {
        val scale = context.resources.displayMetrics.density
        return 84.5f * scale + 0.5f
    }

    override fun getVerticalStep(context: Context): Float {
        val scale = context.resources.displayMetrics.density
        return 12f * scale + 0.5f
    }

    /**
     * 因为每个item的大小是根据屏幕的宽度百分比计算出来的
     * 因此这里单独计算图片半高时，也需要用百分比来计算
     */
    override fun getAlignHeight(context: Context): Float {
        val width = context.getScreenWidth().toFloat() / 900 * 88.5f
        return width / 88.5f * 62.5f / 2
    }
}