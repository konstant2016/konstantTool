package com.konstant.develop.dsl.selector

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import kotlinx.android.synthetic.main.item_selector_publish.view.*

/**
 * 时间：2022/3/18 6:20 下午
 * 作者：吕卡
 * 备注：出版社的适配器
 */

class AdapterSelectorPublish() : RecyclerView.Adapter<PublishViewHolder>() {

    private val mPublishList = mutableListOf<Publisher>()
    private var mOnItemClickListener: ((position: Int) -> Unit)? = null

    fun setData(publishList: List<Publisher>) {
        mPublishList.clear()
        mPublishList.addAll(publishList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        mOnItemClickListener = listener
    }

    private fun onItemClicked(view: View, position: Int) {
        val index = mPublishList.indexOfFirst { it.selected }
        if (index > -1) {
            mPublishList[index].selected = false
            updatePosition(view, index)
        }
        mPublishList[position].selected = true
        updatePosition(view, position)
        mOnItemClickListener?.invoke(position)
    }

    private fun updatePosition(view: View, position: Int) {
        view.post {
            if (position in 0 .. this.itemCount){
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selector_publish, parent, false)
        return PublishViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublishViewHolder, position: Int) {
        val name = holder.itemView.publish_name
        val fullName = holder.itemView.publish_full_name
        val bg = holder.itemView.publish_bg
        val data = mPublishList[position]
        name.text = data.name
        if (TextUtils.isEmpty(data.fullName)){
            fullName.visibility = View.GONE
        }else{
            fullName.visibility = View.VISIBLE
            fullName.text = data.fullName
        }
        if (data.selected) {
            bg.setBackgroundColor(Color.parseColor("#F7F7F7"))
        } else {
            bg.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        holder.itemView.setOnClickListener {
            onItemClicked(holder.itemView, position)
        }
    }

    override fun getItemCount() = mPublishList.size
}

class PublishViewHolder(view: View) : RecyclerView.ViewHolder(view)