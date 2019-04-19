package com.konstant.tool.lite.module.extract

import android.content.pm.PackageInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.util.ApplicationUtil
import kotlinx.android.synthetic.main.item_recycler_package_extra.view.*

class AdapterPackage(val list: List<PackageInfo>) : BaseRecyclerAdapter<AdapterPackage.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_package_extra, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = list[position]
        with(holder.itemView) {
            img_package.setImageDrawable(ApplicationUtil.getAppIcon(data))
            name_package.text = "应用名字：${ApplicationUtil.getAppName(data)}"
            number_package.text = "应用标识：${ApplicationUtil.getPackageName(data)}"
        }
    }

    override fun getItemCount() = list.size


    class Holder(view: View) : RecyclerView.ViewHolder(view)

}