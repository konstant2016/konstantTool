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
    fun getHorizontalStep(context: Context): Float

    /**
     * 每一个item的纵向间距
     */
    fun getVerticalStep(context: Context): Float

    /**
     * 获取卡片上间距
     */
    fun getTopPadding(): Float

    /**
     * 获取卡片下间距
     */
    fun getBottomPadding(): Float

    /**
     * 获取卡片左间距
     */
    fun getLeftPadding(): Float

    /**
     * 获取卡片右间距
     */
    fun getRightPadding(): Float

    /**
     * 获取箭头对齐高度
     * 产品要求箭头只能对齐到图片中心，而不是对齐到文字中心
     * 因此这里返回的是图片一半的高度
     */
    fun getAlignHeight(context: Context): Float

    /**
     * 获取标签的高度
     * 在二级页面时，会添加一个上次学到这儿的标签
     */
    fun getTagHeight(context: Context): Float
}

data class TreeNode(
    val xIndex: Int,
    val yIndex: Int
) : Serializable

data class TreeArrow(
    val startX: Int,
    val startY: Int,
    val endX: Int,
    val endY: Int
) : Serializable