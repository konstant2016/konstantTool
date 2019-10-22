package com.konstant.tool.lite.base

import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * 时间：2018/8/3 11:30
 * 作者：吕卡
 * 描述：RecyclerView的Adapter添加ITEM点击事件
 */

abstract class BaseRecyclerAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var mOnItemClickListener: ((View, Int) -> Unit)? = null
    private var mOnItemLongClickListener: ((View, Int) -> Unit)? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener { mOnItemClickListener?.invoke(holder.itemView, position) }
        holder.itemView.setOnLongClickListener { mOnItemLongClickListener?.invoke(holder.itemView, position);true }
    }

    fun setOnItemClickListener(onItemClickListener: ((View, Int) -> Unit)) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: ((View, Int) -> Unit)) {
        mOnItemLongClickListener = onItemLongClickListener
    }

}