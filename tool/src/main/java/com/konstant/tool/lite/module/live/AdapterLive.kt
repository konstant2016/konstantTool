package com.konstant.tool.lite.module.live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.data.bean.live.LiveData
import com.konstant.tool.lite.module.setting.SettingManager
import kotlinx.android.synthetic.main.item_recycler_live.view.*

class AdapterLive(private val liveData: LiveData) : BaseRecyclerAdapter<AdapterLive.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = Holder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recycler_live, viewGroup, false))

    override fun getItemCount() = liveData.channel.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (SettingManager.getShowChinese(KonApplication.context)) {
            holder.itemView.tv_name.text = liveData.channel[position]
        } else {
            holder.itemView.tv_name.text = liveData.channel_en[position]
        }
    }

    class Holder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}