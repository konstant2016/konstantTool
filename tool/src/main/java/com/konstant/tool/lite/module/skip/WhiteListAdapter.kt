package com.konstant.tool.lite.module.skip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_recycler_skip_white_list.view.*

class WhiteListAdapter(private val appDataWrapperList: List<AppDataWrapper>) : BaseRecyclerAdapter<WhiteListAdapter.WhiteListHolder>() {

    class WhiteListHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhiteListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_skip_white_list, parent, false)
        return WhiteListHolder(view)
    }

    override fun getItemCount(): Int {
        return appDataWrapperList.size
    }

    override fun onBindViewHolder(holder: WhiteListHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val appDataWrapper = appDataWrapperList[position]
        holder.itemView.img_package.setImageDrawable(appDataWrapper.icon)
        holder.itemView.name_package.text = appDataWrapper.appName
    }

}