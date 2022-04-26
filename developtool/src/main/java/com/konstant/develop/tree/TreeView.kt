package com.konstant.develop.tree

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.annotation.RequiresApi
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_item_tree_view.view.*
import kotlin.math.abs
import kotlin.math.atan

class TreeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val mArrow by lazy { BitmapFactory.decodeResource(resources, R.drawable.pic_circle) }

    private val mArrowPaint by lazy {
        Paint().apply {
            strokeWidth = 2f
            color = Color.parseColor("#1C3058")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 向上
        drawArrow(canvas, 100, 800, 300, 300)


        // 向下
        drawArrow(canvas, 500, 300, 800, 800)

    }

    private fun drawArrow(canvas: Canvas?, startX: Int, startY: Int, endX: Int, endY: Int) {
        val dx = endX - startX
        val dy = endY - startY
        val arrowDx = mArrow.width.toFloat() / 2
        val arrowDy = arrowDx * dy / dx
        // 画线
        canvas?.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat() - arrowDx, endY.toFloat() - arrowDy, mArrowPaint)
        val matrix = Matrix()

        // 计算旋转角度
        val tan = dy.toDouble() / dx
        val angle = atan(tan) / Math.PI * 180
        matrix.setRotate(angle.toFloat(), mArrow.width.toFloat() / 2, mArrow.height.toFloat() / 2)
        // 旋转后的新图片
        val bitmap = Bitmap.createBitmap(mArrow, 0, 0, mArrow.width, mArrow.height, matrix, true)
        // 计算新图片的左上角坐标，然后绘制
        canvas?.drawBitmap(bitmap, endX.toFloat() - arrowDx - bitmap.width / 2, endY.toFloat() - arrowDy - bitmap.height / 2, mArrowPaint)
    }

}