package com.konstant.develop.cache

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.konstant.develop.R
import kotlinx.android.synthetic.main.activity_glide_cache.*

class GlideCacheActivity : AppCompatActivity() {

    private val url = "https://cosplay.tos-cn-beijing.volces.com/yangcong.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_cache)

        Thread{
            val size = NetworkFileHelper.getFileSizeWithUrl(url)
            Handler(Looper.getMainLooper()).post {
                Glide.with(this).load(url)
                    .signature(ObjectKey(size))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                            if (resource != null){
                                img_view.setImageDrawable(resource)
                            }
                            return true
                        }
                    }).into(img_view)
            }
        }.start()

    }
}