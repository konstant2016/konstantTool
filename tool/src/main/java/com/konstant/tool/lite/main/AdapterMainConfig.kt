package com.konstant.tool.lite.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.data.bean.main.ConfigData
import kotlinx.android.synthetic.main.item_main_config.view.*

class AdapterMainConfig(private val configs: List<ConfigData.ConfigsBean>) : BaseRecyclerAdapter<AdapterMainConfig.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_config, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        val title = configs[position].title
        holder.itemView.item_title.text = title
    }

    override fun getItemCount() = configs.size

    class Holder(view: View) : RecyclerView.ViewHolder(view)
}