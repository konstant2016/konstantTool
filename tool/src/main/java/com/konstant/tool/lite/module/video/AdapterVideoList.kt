package com.konstant.tool.lite.module.video

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter

/**
 * 时间：2019/1/31 18:03
 * 创建：吕卡
 * 描述：视频列表的适配器
 */
class AdapterVideoList(private val videoList: List<Video>) : BaseRecyclerAdapter<AdapterVideoList.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_video, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = videoList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        val video = videoList[position]
        holder.thumb.setImageBitmap(video.bitmap)
        holder.name.text = "视频：${video.title}"
        holder.size.text = "大小：${video.size}"
        holder.duration.text = "时长：${video.duration}"
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val thumb = view.findViewById(R.id.img_thumb) as ImageView
        val name = view.findViewById(R.id.tv_name) as TextView
        val size = view.findViewById(R.id.tv_size) as TextView
        val duration = view.findViewById(R.id.tv_duration) as TextView
    }
}