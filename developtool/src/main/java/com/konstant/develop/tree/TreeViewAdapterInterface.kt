package com.konstant.develop.tree

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import java.io.Serializable

interface TreeViewAdapterInterface {

    /**
     * 获取节点列表
     */
    fun getNodeList(): List<TreeNode>

    /**
     * 获取箭头位置
     */
    fun getArrowList(): List<TreeArrow>

    /**
     * 创建单个View时调用
     */
    fun onCreateView(context: Context, parent: ViewGroup, index: Int): View

    /**
     * 获取箭头的颜色
     */
    fun getArrowColor(): Int

    /**
     * 获取箭头图标
     */
    fun getArrowBitmap(context: Context): Bitmap

    /**
     * 每一个item的横向间距
     */
    fun getHorizontalStep(): Int

    /**
     * 每一个item的纵向间距
     */
    fun getVerticalStep(): Int
}

open class TreeNode(
    val xIndex: Int,
    val yIndex: Int
) : Serializable

open class TreeArrow(
    val startX: Int,
    val startY: Int,
    val endX: Int,
    val endY: Int
) : Serializable