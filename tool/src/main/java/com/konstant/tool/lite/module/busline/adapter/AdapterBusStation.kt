package com.konstant.tool.lite.module.busline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amap.api.services.busline.BusStationItem
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.item_recycler_bus.view.*

/**
 * 时间：2018/7/30 20:26
 * 作者：吕卡
 * 描述：
 */
class AdapterBusStation(val list: List<BusStationItem>) : RecyclerView.Adapter<AdapterBusStation.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_bus, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.itemView) {
            tv_station.text = list[position].busStationName
            line_left.visibility = View.VISIBLE
            line_right.visibility = View.VISIBLE

            if (position == 0) line_left.visibility = View.GONE
            if (position == list.size - 1) line_right.visibility = View.GONE

        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}