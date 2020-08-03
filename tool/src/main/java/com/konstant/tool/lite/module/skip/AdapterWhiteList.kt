package com.konstant.tool.lite.module.skip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_recycler_skip_white_list.view.*

class AdapterWhiteList(private val appDataWrapperList: List<AppDataWrapper>) : BaseRecyclerAdapter<AdapterWhiteList.WhiteListHolder>() {

    class WhiteListHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhiteListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_skip_white_list, parent, false)
        return WhiteListHolder(view)
    }

    override fun getItemCount(): Int {
        return appDataWrapperList.size
    }

    /**
     * 先设置setOnCheckedChangeListener，用于替换旧的的监听
     * 再设置选中状态
     *
     * 如果先设置选中状态，再设置setOnCheckedChangeListener，则有可能触发之前复用的监听，导致混乱
     */
    override fun onBindViewHolder(holder: WhiteListHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val appDataWrapper = appDataWrapperList[position]
        holder.itemView.img_package.setImageDrawable(appDataWrapper.icon)
        holder.itemView.name_package.text = appDataWrapper.appName
        val checkBox = holder.itemView.view_check_box
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            appDataWrapper.isChecked = isChecked
        }
        holder.itemView.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }
        holder.itemView.view_check_box.isChecked = appDataWrapper.isChecked
    }
}