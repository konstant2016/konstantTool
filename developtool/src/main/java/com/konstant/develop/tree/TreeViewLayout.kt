package com.konstant.develop.tree

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.konstant.develop.utils.SizeUtil
import kotlin.math.*
import kotlin.properties.Delegates

/**
 * 时间：2022/4/29 16:42
 * 作者：吕卡
 * 备注：图谱排列的ViewGroup
 */

class TreeViewLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private val mMatrix by lazy { Matrix() }
    private var mScale = 1f

    private var mTotalWidth = 0f
    private var mTotalHeight = 0f

    private var mAdapter: TreeViewAdapterInterface? = null
    private var mItemWidth = 0f
    private var mItemHeight = 0f

    // 箭头图标
    private var mArrow: Bitmap by Delegates.notNull()
    private val sizeUtil by lazy { SizeUtil(context) }

    private val mArrowPaint by lazy {
        Paint().apply {
            strokeWidth = 1f
            isAntiAlias = true
        }
    }

    init {
        setWillNotDraw(false)
        clipChildren = false
        clipToPadding = false
    }

    /**
     * 重新排布每一个ITEM的位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val layoutParams = (child.layoutParams as LayoutParams)
            val x = layoutParams.x
            val y = layoutParams.y
            val width = layoutParams.width
            val height = layoutParams.height
            val left = x - (width.toFloat() / 2)
            val top = y - (height.toFloat() / 2)
            val right = x + (width.toFloat() / 2)
            val bottom = y + (height.toFloat() / 2)
            child.layout(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }

    fun setAdapter(adapter: TreeViewAdapterInterface) {
        mAdapter = adapter
        onAdapter()
    }

    private fun onAdapter() {
        val adapter = mAdapter ?: return
        mArrow = adapter.getArrowBitmap(context)
        mArrowPaint.color = adapter.getArrowColor()
        val count = adapter.getNodeList().size
        if (count > 0) {
            val itemView = adapter.onCreateView(context, this, 0)
            mItemWidth = itemView.layoutParams.width.toFloat()
            mItemHeight = itemView.layoutParams.height.toFloat()
        }
        removeAllViews()
        invalidate()
        addView(adapter)
    }

    private fun addView(adapter: TreeViewAdapterInterface) {
        val list = adapter.getNodeList()
        var maxX = 0
        var maxY = 0
        list.forEachIndexed { position, node ->
            val view = adapter.onCreateView(context, this, position)
            val xIndex = node.xIndex
            val yIndex = node.yIndex
            val x = xIndex * adapter.getHorizontalStep(context) + xIndex * mItemWidth + mItemWidth / 2
            val y = yIndex * adapter.getVerticalStep(context) + yIndex * mItemHeight + mItemHeight / 2
            val layoutParams = LayoutParams(mItemWidth.toInt(), mItemHeight.toInt(), x, y)
            addView(view, layoutParams)
            // 计算总宽高
            if (xIndex > maxX) {
                maxX = xIndex
            }
            if (yIndex > maxY) {
                maxY = yIndex
            }
        }
        mTotalWidth = mItemWidth * (maxX + 1) + adapter.getHorizontalStep(context) * maxX + 10
        mTotalHeight = mItemHeight * (maxY + 1) + adapter.getVerticalStep(context) * maxY + 10
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val adapter = mAdapter ?: return
        mMatrix.reset()
        mMatrix.postScale(mScale, mScale)
        canvas?.save()
        canvas?.concat(mMatrix)
        val relationList = adapter.getArrowList()
        canvas?.let {
            sortArrow(it, relationList)
        }
    }

    /**
     * 归类为斜线、垂线、横线
     */
    private fun sortArrow(canvas: Canvas, arrowList: List<TreeArrow>) {
        val verticalList = mutableListOf<TreeArrow>()
        val sideList = mutableListOf<TreeArrow>()
        val horizontalList = mutableListOf<TreeArrow>()
        arrowList.forEach { relation ->
            when {
                relation.startX == relation.endX -> {
                    verticalList.add(relation)
                }
                relation.startY == relation.endY -> {
                    horizontalList.add(relation)
                }
                else -> {
                    sideList.add(relation)
                }
            }
        }
        drawVerticalArrow(canvas, verticalList)
        drawSideArrow(canvas, sideList)
        drawHorizontalArrow(canvas, horizontalList)
    }

    /**
     * 画斜线
     */
    private fun drawSideArrow(canvas: Canvas, arrowList: List<TreeArrow>) {
        val adapter = mAdapter ?: return
        arrowList.forEach { relation ->
            val startX = (relation.startX + 1) * mItemWidth + relation.startX * adapter.getHorizontalStep(context) - adapter.getRightPadding()
            val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context) + adapter.getTopPadding() + (mItemHeight - adapter.getTopPadding() - adapter.getBottomPadding()) / 2
            val endX = relation.endX * mItemWidth + relation.endX * adapter.getHorizontalStep(context) + adapter.getLeftPadding()
            val endY = relation.endY * mItemHeight + relation.endY * adapter.getVerticalStep(context) + +adapter.getTopPadding() + (mItemHeight - adapter.getTopPadding() - adapter.getBottomPadding()) / 2
            drawSideArrow(canvas, startX, startY, endX, endY)
        }
    }

    /**
     * 画垂直方向上的线
     */
    private fun drawVerticalArrow(canvas: Canvas, arrowList: List<TreeArrow>) {
        val adapter = mAdapter ?: return
        arrowList.forEach { relation ->
            // 从上往下画线
            if (relation.endY > relation.startY) {
                val startX = relation.startX * mItemWidth + relation.startX * adapter.getHorizontalStep(context) + adapter.getLeftPadding() + (mItemWidth - adapter.getLeftPadding() - adapter.getRightPadding()) / 2
                val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context) + mItemHeight - adapter.getBottomPadding()
                val endX = startX
                val endY = relation.endY * mItemHeight + relation.endY * adapter.getVerticalStep(context) + adapter.getTopPadding()
                drawVerticalArrow(canvas, startX, startY, endX, endY)
            } else {  // 从下往上画线
                val startX = relation.startX * mItemWidth + relation.startX * adapter.getHorizontalStep(context) + adapter.getLeftPadding() + (mItemWidth - adapter.getLeftPadding() - adapter.getRightPadding()) / 2
                val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context) + adapter.getTopPadding()
                val endX = startX
                val endY = (relation.endY + 1) * mItemHeight + relation.endY * adapter.getVerticalStep(context) - adapter.getBottomPadding()
                drawVerticalArrow(canvas, startX, startY, endX, endY)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mTotalWidth.toInt(), mTotalHeight.toInt())
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    private fun addView(child: View, layoutParams: LayoutParams) {
        super.addView(child, layoutParams)
    }

    /**
     * 垂直方向画线，不需要x方向给箭头留位置，但是需要Y方向给箭头留位置
     * 需要区分从下往上画和从上往下画
     *      从上往下话，则终点的Y值需要减去预留高度
     *      反之，则需要添加预留高度
     */
    private fun drawVerticalArrow(canvas: Canvas?, startX: Float, startY: Float, endX: Float, endY: Float) {
        val height = mArrow.height.toFloat()
        if (endY > startY) {    // 从上往下画
            canvas?.drawLine(startX, startY, endX, endY - height, mArrowPaint)
            canvas?.drawBitmap(mArrow, endX - mArrow.width.toFloat() / 2, endY - mArrow.height, mArrowPaint)
        } else {    // 从下往上画
            canvas?.drawLine(startX, startY, endX, endY + height, mArrowPaint)
            canvas?.drawBitmap(mArrow, endX - mArrow.width.toFloat() / 2, endY, mArrowPaint)
        }
    }

    /**
     * 画斜线
     * 这个地方需要用到三角函数来计算各个坐标，需要对着设计图理解，否则无法看懂逻辑
     * 设计图：https://lanhuapp.com/web/#/item/project/detailDetach?pid=d8532a1c-268c-42fd-ac2d-25616f2a9379&image_id=7948e49e-e680-4171-96b8-5150df3b8918&project_id=d8532a1c-268c-42fd-ac2d-25616f2a9379&fromEditor=true&type=image
     *
     * point 3    point 5
     */
    private fun drawSideArrow(canvas: Canvas?, startX: Float, startY: Float, endX: Float, endY: Float) {
        canvas?.drawBitmap(mArrow, endX - mArrow.width, endY - mArrow.height.toFloat() / 2, mArrowPaint)
        val isUp = endY < startY
        // x1与x6的值是写死的，x3 、x4 、x5 的值是变化的，需要计算
        val x1 = sizeUtil.resetValue(45f)

        val line2 = sizeUtil.resetValue(208f)
        val lineSpace = sizeUtil.resetValue(22f)
        val line4 = sizeUtil.resetValue(68f)

        // x 方向上的偏移量
        val dx = endX - x1 - startX
        // y方向上的偏移量
        val dy = abs(startY - endY)
        val tan = dy.toDouble() / dx
        // 整个走势的旋转角度 , toRadians弧度转角度
        val angleWhole = Math.toDegrees(atan(tan))

        // 线条的旋转角度
        val x2 = line2 * cos(Math.toRadians(angleWhole))
        val y2 = line2 * sin(Math.toRadians(angleWhole))

        val angle = 135
        val angle1 = 90 - angleWhole + 90 + angle
        val x3 = lineSpace * cos(Math.toRadians(angle1))
        val y3 = lineSpace * sin(Math.toRadians(angle1))

        val x4 = line4 * cos(Math.toRadians(angleWhole))
        val y4 = line4 * sin(Math.toRadians(angleWhole))

        val angle2 = 360 - (angle - (90 - angleWhole) - 90)
        val x5 = lineSpace * cos(Math.toRadians(angle2))
        val y5 = lineSpace * sin(Math.toRadians(angle2))

        val point0X = startX
        val point0Y = startY

        val point1X = startX + x1
        val point1Y = startY + 0

        val point2X = point1X + x2
        val point2Y = if (isUp) {
            point1Y - y2
        } else {
            point1Y + y2
        }

        val point3X = point2X + x3
        val point3Y = if (isUp) {
            point2Y + y3
        } else {
            point2Y - y3
        }

        val point4X = point3X + x4
        val point4Y = if (isUp) {
            point3Y - y4
        } else {
            point3Y + y4
        }

        val point5X = point4X + x5
        val point5Y = if (isUp) {
            point4Y + y5
        } else {
            point4Y - y5
        }

        val point6X = endX - mArrow.width
        val point6Y = endY

        canvas?.drawLine(point0X, point0Y, point1X, point1Y, mArrowPaint)
        canvas?.drawLine(point1X, point1Y, point2X.toFloat(), point2Y.toFloat(), mArrowPaint)
        canvas?.drawLine(point2X.toFloat(), point2Y.toFloat(), point3X.toFloat(), point3Y.toFloat(), mArrowPaint)
        canvas?.drawLine(point3X.toFloat(), point3Y.toFloat(), point4X.toFloat(), point4Y.toFloat(), mArrowPaint)
        canvas?.drawLine(point4X.toFloat(), point4Y.toFloat(), point5X.toFloat(), point5Y.toFloat(), mArrowPaint)
        canvas?.drawLine(point5X.toFloat(), point5Y.toFloat(), point6X, point6Y, mArrowPaint)
    }

    /**
     * 画横线
     */
    private fun drawHorizontalArrow(canvas: Canvas?, arrowList: List<TreeArrow>) {
        val adapter = mAdapter ?: return
        arrowList.forEach { relation ->
            val startX = (relation.startX + 1) * mItemWidth + relation.startX * adapter.getHorizontalStep(context) - adapter.getRightPadding()
            val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context) + adapter.getTopPadding() + (mItemHeight - adapter.getTopPadding() - adapter.getBottomPadding()) / 2
            val endX = relation.endX * mItemWidth + relation.endX * adapter.getHorizontalStep(context) + adapter.getLeftPadding()
            val endY = relation.endY * mItemHeight + relation.endY * adapter.getVerticalStep(context) + +adapter.getTopPadding() + (mItemHeight - adapter.getTopPadding() - adapter.getBottomPadding()) / 2
            canvas?.drawLine(startX, startY, endX - mArrow.width, endY, mArrowPaint)
            canvas?.drawBitmap(mArrow, endX - mArrow.width, endY - mArrow.height.toFloat() / 2, mArrowPaint)
        }
    }

    class LayoutParams(width: Int, height: Int, val x: Float, val y: Float) : ViewGroup.LayoutParams(width, height)
}