package com.konstant.test

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class GlideImageLoader():ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        val s = "http://img5.duitang.com/uploads/item/201412/19/20141219132442_JTuSj.jpeg"
        Glide.with(context).load(s).into(imageView)
    }
}