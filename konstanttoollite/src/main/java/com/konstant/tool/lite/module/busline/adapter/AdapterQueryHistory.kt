package com.konstant.tool.lite.module.busline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.module.busline.data.QueryWrapper

/**
 * 时间：2018/8/3 9:50
 * 作者：吕卡
 * 描述：
 */
class AdapterQueryHistory(val list: ArrayList<QueryWrapper>) : BaseRecyclerAdapter<AdapterQueryHistory.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_query_history, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        setData(holder, list[position])
    }

    private fun setData(holder: Holder, queryWrapper: QueryWrapper) {
        val city = holder.view.findViewById(R.id.tv_city) as TextView
        val route = holder.view.findViewById(R.id.tv_route) as TextView
        city.text = "城市：${queryWrapper.cityName}"
        route.text = "关键字：${queryWrapper.route}"
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}