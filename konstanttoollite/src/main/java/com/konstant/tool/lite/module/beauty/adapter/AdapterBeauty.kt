package com.konstant.tool.lite.module.beauty.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.beauty.activity.LookPictureActivity
import com.squareup.picasso.Picasso

/**
 * 描述:看美女图片的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/4 下午2:30
 * 备注:
 */

class AdapterBeauty(val urls: ArrayList<String>, val context: Context) : RecyclerView.Adapter<AdapterBeauty.NormalHolder>() {

    private val imgList = ArrayList<ImageView>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NormalHolder {

        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_normal, parent, false) as ImageView

        return NormalHolder(view)

    }

    override fun getItemCount(): Int = urls.size


    override fun onBindViewHolder(holder: NormalHolder, position: Int) {
        Picasso.with(context).load(urls[position]).into(holder.view)
        holder.view.setOnClickListener {
            val intent = Intent(context, LookPictureActivity::class.java)
            intent.putExtra("urlString", urls[position])
            intent.putStringArrayListExtra("urlList", urls)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
        imgList.add(holder.view)
    }


    fun getImgList() = imgList

    // 默认的holder
    class NormalHolder(val view: ImageView) : RecyclerView.ViewHolder(view)

}