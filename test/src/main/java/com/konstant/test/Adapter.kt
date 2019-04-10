package com.konstant.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.Banner

class Adapter(list: List<Data>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val banners = ArrayList<String>()
    private val imgs = ArrayList<String>()

    init {
        imgs.add("")
        list.forEachIndexed { index, data ->
            if (index > 1) {
                imgs.add(data.imgUrl)
            } else {
                banners.add(data.imgUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if (type == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.my_banner, parent, false)
            return BannerHolder(view as Banner)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.imageview, parent, false)
        return Holder(view as ImageView)
    }

    override fun getItemCount() = imgs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        when (holder) {
            is BannerHolder -> {
                with(holder.view) {
                    setImageLoader(GlideImageLoader())
                    setImages(banners)
                    start()
                }
            }
            is Holder -> {
                val s = "http://img5.duitang.com/uploads/item/201412/19/20141219132442_JTuSj.jpeg"
                Glide.with(context).load(s).into(holder.view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        return 1
    }

    class Holder(val view: ImageView) : RecyclerView.ViewHolder(view)

    class BannerHolder(val view: Banner) : RecyclerView.ViewHolder(view)

}