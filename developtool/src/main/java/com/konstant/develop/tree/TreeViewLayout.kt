package com.konstant.develop.tree

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.atan
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

    private val mArrowPaint by lazy {
        Paint().apply {
            strokeWidth = 2f
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
     * 归类为斜箭头和垂直的线头
     */
    private fun sortArrow(canvas: Canvas, arrowList: List<TreeArrow>) {
        val verticalList = mutableListOf<TreeArrow>()
        val sideList = mutableListOf<TreeArrow>()
        arrowList.forEach { relation ->
            if (relation.startX == relation.endX) {
                verticalList.add(relation)
            } else {
                sideList.add(relation)
            }
        }
        drawVerticalArrow(canvas, verticalList)
        drawSideArrow(canvas, sideList)
    }

    /**
     * 画斜线
     * 斜线需要区分多个箭头怼到一个重点的情况，这种情况需要把箭头分开
     */
    private fun drawSideArrow(canvas: Canvas, arrowList: List<TreeArrow>) {
        val adapter = mAdapter ?: return
        val map = mutableMapOf<TreeNode, MutableList<TreeNode>>()
        arrowList.forEach { relation ->
            val startNode = TreeNode(relation.startX, relation.startY)
            val endNode = TreeNode(relation.endX, relation.endY)
            if (map.contains(endNode)) {
                val list = map[endNode]
                list?.add(startNode)
            } else {
                val list = mutableListOf<TreeNode>()
                list.add(startNode)
                map[endNode] = list
            }
        }
        map.forEach { item ->
            val endNode = item.key
            val startNodeList = item.value
            val height = mArrow.height
            val horizontalCount = startNodeList.count { it.yIndex == endNode.yIndex }
            val upperList = startNodeList.filter { it.yIndex > endNode.yIndex }
            val downList = startNodeList.filter { it.yIndex < endNode.yIndex }
            startNodeList.forEach { startNode ->
                val startX = (startNode.xIndex + 1) * mItemWidth + startNode.xIndex * adapter.getHorizontalStep(context)
                val startY = startNode.yIndex * mItemHeight + startNode.yIndex * adapter.getVerticalStep(context) + adapter.getAlignHeight(context)
                val endX = endNode.xIndex * mItemWidth + endNode.xIndex * adapter.getHorizontalStep(context)
                val y = endNode.yIndex * mItemHeight + endNode.yIndex * adapter.getVerticalStep(context) + adapter.getAlignHeight(context)

                val endY1 = when {
                    // 如果只有一条线，直接居中绘制即可
                    startNodeList.size == 1 -> y
                    // 如果从下往上绘制，则判断当前箭头属于从下往上绘制的第几条，用来计算偏移量
                    // 第一条偏移一个箭头高度，第二条，偏移两个箭头高度，以此类推
                    startNode.yIndex > endNode.yIndex -> {
                        val count = upperList.indexOfFirst { it.yIndex == startNode.yIndex }
                        y + (count + horizontalCount) * height
                    }
                    // 如果是从上往下绘制，则根据偏移量进行减计算
                    startNode.yIndex < endNode.yIndex -> {
                        val count = downList.indexOfFirst { it.yIndex == startNode.yIndex }
                        y - (count + horizontalCount) * height
                    }
                    // 如果是水平绘制，则不需要加减
                    else -> {
                        y
                    }
                }
                drawArrow(canvas, startX, startY, endX, endY1)
            }
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
                val startX = (relation.startX + 1) * mItemWidth + relation.startX * adapter.getHorizontalStep(context) - (mItemWidth / 2).toInt()
                val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context) + adapter.getAlignHeight(context) * 2
                val endX = startX
                val endY = relation.endY * mItemHeight + relation.endY * adapter.getVerticalStep(context)
                drawVerticalArrow(canvas, startX, startY, endX, endY)
            } else {  // 从下往上画线
                val startX = (relation.startX + 1) * mItemWidth + relation.startX * adapter.getHorizontalStep(context) - (mItemWidth / 2).toInt()
                val startY = relation.startY * mItemHeight + relation.startY * adapter.getVerticalStep(context)
                val endX = startX
                val endY = (relation.endY + 1) * mItemHeight + relation.endY * adapter.getVerticalStep(context) - (mItemHeight - adapter.getAlignHeight(context) * 2)
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
     * x1,y1,x2,y2 代表的是开始和结束的坐标
     * 绘制箭头时，需要把线的长度裁掉一段，再绘制箭头，否则箭头会覆盖到后面的view
     * 因为是从左往右排布的，因此只需要x方向裁掉一部分即可
     *
     * 根据裁掉后的终点坐标，计算箭头中心点的坐标
     * 利用三角函数计算xy方向的偏移量，然后用上面计算的终点坐标减掉这个偏移量，即可得到箭头的起始左上角坐标
     */
    private fun drawArrow(canvas: Canvas?, startX: Float, startY: Float, endX: Float, endY: Float) {
        val dx = endX - startX
        val dy = endY - startY
        val arrowDx = mArrow.width.toFloat() / 2
        // 画线
        canvas?.drawLine(startX, startY, endX - arrowDx, endY, mArrowPaint)
        // 旋转生成新图片
        val matrix = Matrix()
        val tan = dy.toDouble() / dx
        val angle = atan(tan) / Math.PI * 180
        matrix.setRotate(angle.toFloat(), mArrow.width.toFloat() / 2, mArrow.height.toFloat() / 2)
        val bitmap = Bitmap.createBitmap(mArrow, 0, 0, mArrow.width, mArrow.height, matrix, true)
        // 计算新图片的左上角坐标，然后绘制
        canvas?.drawBitmap(bitmap, endX - arrowDx - bitmap.width / 2, endY - bitmap.height / 2, mArrowPaint)
    }

    /**
     * 垂直方向画线，不需要x方向给箭头留位置，但是需要Y方向给箭头留位置
     * 需要区分从下往上画和从上往下画
     *      从上往下话，则终点的Y值需要减去预留高度
     *      反之，则需要添加预留高度
     */
    private fun drawVerticalArrow(canvas: Canvas?, startX: Float, startY: Float, endX: Float, endY: Float) {
        val height = mArrow.height.toFloat() / 2
        val matrix = Matrix()
        if (endY > startY) {    // 从上往下画
            canvas?.drawLine(startX, startY, endX, endY - height, mArrowPaint)
            matrix.setRotate(90f, mArrow.width.toFloat() / 2, mArrow.height.toFloat() / 2)
            val bitmap = Bitmap.createBitmap(mArrow, 0, 0, mArrow.width, mArrow.height, matrix, true)
            canvas?.drawBitmap(bitmap, endX - bitmap.width / 2, endY - bitmap.height, mArrowPaint)
        } else {    // 从下往上画
            canvas?.drawLine(startX, startY, endX, endY + height, mArrowPaint)
            matrix.setRotate(-90f, mArrow.width.toFloat() / 2, mArrow.height.toFloat() / 2)
            val bitmap = Bitmap.createBitmap(mArrow, 0, 0, mArrow.width, mArrow.height, matrix, true)
            canvas?.drawBitmap(bitmap, endX - bitmap.width / 2, endY, mArrowPaint)
        }
    }

    class LayoutParams(width: Int, height: Int, val x: Float, val y: Float) : ViewGroup.LayoutParams(width, height)
}