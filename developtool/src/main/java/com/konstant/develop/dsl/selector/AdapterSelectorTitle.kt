package com.konstant.develop.dsl.selector

import android.graphics.Color
import android.text.method.MultiTapKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import kotlinx.android.synthetic.main.item_selector_title.view.*

/**
 * 时间：2022/3/18 6:08 下午
 * 作者：吕卡
 * 备注：选择弹窗的标题适配器
 */

class AdapterSelectorTitle : RecyclerView.Adapter<TitleViewHolder>() {

    private val mStageList = mutableListOf<Stage>()

    private var mOnItemClickListener: ((position: Int) -> Unit)? = null

    fun setData(stageList: List<Stage>) {
        mStageList.clear()
        mStageList.addAll(stageList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        mOnItemClickListener = listener
    }

    private fun onItemClicked(view: View, position: Int) {
        val index = mStageList.indexOfFirst { it.selected }
        if (index > -1) {
            mStageList[index].selected = false
            updatePosition(view, index)
        }
        mStageList[position].selected = true
        updatePosition(view, position)
        mOnItemClickListener?.invoke(position)
    }

    private fun updatePosition(view: View, position: Int) {
        view.post {
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selector_title, parent, false)
        return TitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val title = holder.itemView.stage_title
        val line = holder.itemView.stage_line
        val data = mStageList[position]
        title.text = data.name
        if (data.selected) {
            title.setTextColor(Color.parseColor("#FF518AFF"))
            line.setBackgroundColor(Color.parseColor("#FF518AFF"))
        } else {
            title.setTextColor(Color.parseColor("#666666"))
            line.setBackgroundColor(Color.parseColor("#00000000"))
        }
        holder.itemView.setOnClickListener {
            onItemClicked(holder.itemView, position)
        }
    }

    override fun getItemCount() = mStageList.size
}

class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view)


