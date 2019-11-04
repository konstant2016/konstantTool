package com.konstant.tool.lite.module.live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_recycler_live.view.*

class AdapterLive(private val channelList: List<String>) : BaseRecyclerAdapter<AdapterLive.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = Holder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recycler_live, viewGroup, false))

    override fun getItemCount() = channelList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.tv_name.text = channelList[position]
    }

    class Holder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}