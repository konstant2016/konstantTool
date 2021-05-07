package com.konstant.tool.lite.module.wxfake

import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.util.FileUtil

class AdapterFake(val list: List<Conversion>) : BaseRecyclerAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ConversionType.TYPE_ADVERSE -> {
                AdverseHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_conversion_adverse, viewGroup, false))
            }
            ConversionType.TYPE_MINE -> {
                MineHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_conversion_mine, viewGroup, false))
            }
            else -> {
                TimeHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_conversion_time, viewGroup, false))
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) = list[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val conversion = list[position]
        if (holder is TimeHolder) {
            onConvertTimeHolder(holder, conversion)
            return
        }
        onConvertHolder(holder, conversion)
    }

    private fun onConvertTimeHolder(holder: TimeHolder, conversion: Conversion) {
        (holder.itemView as TextView).text = conversion.msg
    }

    private fun onConvertHolder(holder: RecyclerView.ViewHolder, conversion: Conversion) {
        with(holder.itemView) {
            val bitmap = FileUtil.getPrivateBitmap(context, conversion.fileName)
                    ?: BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
            with(RoundedBitmapDrawableFactory.create(resources, bitmap)) {
                paint.isAntiAlias = true
                cornerRadius = 50F
                findViewById<ImageView>(R.id.img_header).setImageDrawable(this)
            }
            findViewById<TextView>(R.id.tv_msg).text = conversion.msg
        }
    }

    // 对方
    class AdverseHolder(view: View) : RecyclerView.ViewHolder(view)

    // 我方
    class MineHolder(view: View) : RecyclerView.ViewHolder(view)

    // 日期
    class TimeHolder(view: View) : RecyclerView.ViewHolder(view)
}