package com.konstant.tool.ui.activity.toolactivity.im.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.R

/**
 * 描述:即时通讯中的聊天列表的ITEM
 * 创建人:菜籽
 * 创建时间:2018/1/11 上午11:54
 * 备注:
 */

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var size = 15

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_nick.text = "小菜"
        holder.tv_msg.text = "最近咋样啊？"
        holder.tv_time.text = "12:43"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_conversion_chat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_nick = itemView.findViewById(R.id.nick_item) as TextView
        val tv_msg = itemView.findViewById(R.id.msg_item) as TextView
        val tv_time = itemView.findViewById(R.id.time_item) as TextView
        val img_header = itemView.findViewById(R.id.header_item) as ImageView
    }
}