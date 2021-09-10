package com.konstant.tool.ui.activity.cardSwipe

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

class CardDragCallback(private val mRecyclerView: RecyclerView, private val list: MutableList<String>) : ItemTouchHelper.Callback() {

    private var mListener: ((direction: DragStatus) -> Unit)? = null

    enum class DragStatus {
        LEFT, CENTER, RIGHT, RELEASED
    }

    /**
     * 监听左右滑动时的回调
     */
    fun setOnDragListener(listener: (direction: DragStatus) -> Unit) {
        mListener = listener
    }

    /**
     * dragFlags 指的是拖动方向
     * swipeFlags 指的是滑动消失的方向
     * 但是dragFlags跟注释的不太一样，有点迷糊
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (target is SwipeCardAdapter.SwipeCardViewHolder){
            target.reverseCardToBg()
        }
        return false
    }

    /**
     * 每次滑动结束后，刷新一下，用于修正item的位置
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.itemView.rotation = 0f
        val obj = list.removeAt(viewHolder.adapterPosition)
        list.add(0, obj)
        mRecyclerView.adapter?.notifyDataSetChanged()
        mListener?.invoke(DragStatus.RELEASED)
    }

    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val swipeValue = sqrt(dX * dX + dY * dY.toDouble())
        var fraction: Double = swipeValue / getThreshold(recyclerView)
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1.0
        }
        val childCount = recyclerView.childCount
        // 对所有的item做逐级缩小绘制
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val level = childCount - i - 1
            if (level > 0) {
                child.scaleX = (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP).toFloat()
                child.scaleY = (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP).toFloat()
                child.translationY = (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP).toFloat()
            } else {
                //探探只是第一层加了rotate & alpha的操作
                //不过他区分左右
                var xFraction: Float = dX / getThreshold(recyclerView)
                //边界修正 最大为1
                if (xFraction > 1) {
                    xFraction = 1f
                } else if (xFraction < -1) {
                    xFraction = -1f
                }
                //rotate
                child.rotation = xFraction * 15
            }
        }

        // 左右滑动的回调处理
        val value: Float = recyclerView.width / 2 - viewHolder.itemView.x - viewHolder.itemView.width / 2
        when {
            value > recyclerView.width / 4 -> {
                mListener?.invoke(DragStatus.LEFT)
            }
            value < -recyclerView.width / 4 -> {
                mListener?.invoke(DragStatus.RIGHT)
            }
            else -> {
                mListener?.invoke(DragStatus.CENTER)
            }
        }
    }

    //水平方向是否可以被回收掉的阈值
    private fun getThreshold(recyclerView: RecyclerView): Float {
        return recyclerView.width * 0.5f
    }

}