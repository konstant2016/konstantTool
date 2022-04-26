package com.konstant.develop.tree

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.times
import androidx.core.view.drawToBitmap
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_item_tree_view.view.*
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.properties.Delegates

class TreeViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private val mMatrix by lazy { Matrix() }
    private var mScale = 1f

    // 每个item的间隔距离
    private val itemStepWidth = 180
    private val itemStepHeight = 50

    private var mTotalWidth = 0
    private var mTotalHeight = 0

    private var mAdapter: TreeViewAdapter by Delegates.notNull()
    private var mItemWidth = 0
    private var mItemHeight = 0

    private var maxX = 0
    private var maxY = 0

    // 箭头图标
    private val mArrow by lazy { BitmapFactory.decodeResource(resources, R.drawable.icon_jiantou) }

    private val mArrowPaint by lazy {
        Paint().apply {
            strokeWidth = 1f
            isAntiAlias = true
            color = Color.parseColor("#1C3058")
        }
    }

    init {
        setWillNotDraw(false)
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

    fun setAdapter(adapter: TreeViewAdapter) {
        mAdapter = adapter
        onAdapter()
    }

    private fun onAdapter() {
        val count = mAdapter.getItemCount()
        if (count > 0) {
            val itemView = mAdapter.onCreateView(context, 0, this)
            mItemWidth = itemView.layoutParams.width
            mItemHeight = itemView.layoutParams.height
        }

        addView(mAdapter.getDataList())

        mTotalWidth = mItemWidth * (maxX + 1) + itemStepWidth * maxX
        mTotalHeight = mItemHeight * (maxY + 1) + itemStepHeight * maxY
        makeLog("totalWidth:${mTotalWidth}----totalHeight:${mTotalHeight}")
    }

    private fun addView(list: List<Response>) {
        list.forEachIndexed { position, response ->
            val view = mAdapter.onCreateView(context, position, this)
            val xIndex = response.x
            val yIndex = response.y
            makeLog("setAdapter:-----width:${mItemWidth}---height:${mItemHeight}")
            val x = xIndex * itemStepWidth + xIndex * mItemWidth + mItemHeight / 2
            val y = yIndex * itemStepHeight + yIndex * mItemWidth + mItemHeight / 2
            val layoutParams = LayoutParams(mItemWidth, mItemHeight, x, y)
            addView(view, layoutParams)
            // 计算总宽高
            if (xIndex > maxX) {
                maxX = xIndex
            }
            if (yIndex > maxY) {
                maxY = yIndex
            }
            response.nextList?.let {
                addView(it)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mMatrix.reset()
        mMatrix.postScale(mScale, mScale)
        canvas?.save()
        canvas?.concat(mMatrix)
        val list = mAdapter.getDataList()
        list.forEach { start ->
            drawListLine(canvas, start)
        }
    }

    private fun drawListLine(canvas: Canvas?, start: Response) {
        start.nextList?.forEachIndexed { index, end ->
            val startX = (start.x + 1) * mItemWidth + start.x * itemStepWidth
            val startY = start.y * mItemHeight + start.y * itemStepHeight + mItemHeight / 2 /*start.nextList.size * (index + 1)*/
            val endX = end.x * mItemWidth + end.x * itemStepWidth
            val endY = end.y * mItemHeight + end.y * itemStepHeight + mItemHeight / 2
            drawArrow(canvas, startX, startY, endX, endY)
            drawListLine(canvas, end)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mTotalWidth, mTotalHeight)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        makeLog("onMeasure")
    }

    private fun addView(child: View, layoutParams: LayoutParams) {
        super.addView(child, layoutParams)
    }

    private fun makeLog(string: String) {
        Log.d("TreeViewGroup", string)
    }

    /**
     *
     *
     * x1,y1,x2,y2 代表的是开始和结束的坐标
     * 绘制箭头时，需要把线的长度裁掉一段，再绘制箭头，否则箭头会覆盖到后面的view
     * 因为是从左往右排布的，因此只需要x方向裁掉一部分即可
     *
     * 根据裁掉后的终点坐标，计算箭头中心点的坐标
     * 利用三角函数计算xy方向的偏移量，然后用上面计算的终点坐标减掉这个偏移量，即可得到箭头的起始左上角坐标
     */
    private fun drawArrow(canvas: Canvas?, startX: Int, startY: Int, endX: Int, endY: Int) {
        val dx = endX - startX
        val dy = endY - startY
        val arrowDx = mArrow.width.toFloat() / 2
        val arrowDy = arrowDx * dy / dx
        // 画线
        canvas?.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat() - arrowDx, endY.toFloat() - arrowDy, mArrowPaint)
        // 旋转生成新图片
        val matrix = Matrix()
        val tan = dy.toDouble() / dx
        val angle = atan(tan) / Math.PI * 180
        matrix.setRotate(angle.toFloat(), mArrow.width.toFloat() / 2, mArrow.height.toFloat() / 2)
        val bitmap = Bitmap.createBitmap(mArrow, 0, 0, mArrow.width, mArrow.height, matrix, true)
        // 计算新图片的左上角坐标，然后绘制
        canvas?.drawBitmap(bitmap, endX.toFloat() - arrowDx - bitmap.width / 2, endY.toFloat() - arrowDy - bitmap.height / 2, mArrowPaint)
    }


    class LayoutParams(width: Int, height: Int, val x: Int, val y: Int) : ViewGroup.LayoutParams(width, height)
}