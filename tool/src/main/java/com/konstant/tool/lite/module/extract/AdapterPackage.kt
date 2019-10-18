package com.konstant.tool.lite.module.extract

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_recycler_package_extra.view.*

class AdapterPackage(val list: List<AppData>) : BaseRecyclerAdapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_package_header, parent, false)
            TopHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_package_extra, parent, false)
            CommonHolder(view)
        }
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = list[position]
        if (holder is CommonHolder) {
            with(holder.itemView) {
                img_package.setImageDrawable(data.icon)
                name_package.text = "应用名字：${data.appName}"
                number_package.text = "应用标识：${data.packageName}"
            }
        }
        if (holder is TopHolder) {
            holder.itemView.setOnClickListener { }
            holder.itemView.setOnLongClickListener { false }
        }
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = list.size


    class CommonHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    class TopHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}