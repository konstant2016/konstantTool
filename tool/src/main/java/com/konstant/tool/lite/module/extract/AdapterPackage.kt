package com.konstant.tool.lite.module.extract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.base.KonApplication
import kotlinx.android.synthetic.main.item_recycler_package_extra.view.*

class AdapterPackage(val list: List<AppData>) : BaseRecyclerAdapter<AdapterPackage.CommonHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_package_extra, parent, false)
        return CommonHolder(view)
    }

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = list[position]
        with(holder.itemView) {
            img_package.setImageDrawable(data.icon)
            name_package.text = "${KonApplication.context.getString(R.string.package_app_name)}：${data.appName}"
            number_package.text = "${KonApplication.context.getString(R.string.package_app_package)}：${data.packageName}"
        }
    }

    override fun getItemCount() = list.size

    class CommonHolder(view: View) : RecyclerView.ViewHolder(view)

}