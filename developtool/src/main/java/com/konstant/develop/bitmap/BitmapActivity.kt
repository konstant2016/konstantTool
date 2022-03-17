package com.konstant.develop.bitmap

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_bitmap.*
import java.io.ByteArrayOutputStream


@SuppressLint("NewApi")
class BitmapActivity : BaseActivity() {

    private val TAG = "BitmapActivity"

    companion object {
        private val list = mutableListOf<Bitmap>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap)
        loadImg()
    }

    private fun loadImg() {
        Thread {
            loadAssets()
            loadDefault()
            loadXX()
            sizeCompress()
            qualityCompress()
            rgbCompress()
        }.start()
        glideCompress()
    }

    private fun loadAssets() {
        val bytes = assets.open("big_picture.jpg").readBytes()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        list.add(bitmap)
        Log.d(TAG, "原始图片大小:${bitmap.allocationByteCount / 1024.0 / 1024.0}")
    }

    private fun loadDefault() {
        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.big_picture_default)
        val byteCount = decodeResource.allocationByteCount / 1024.0 / 1024.0
        Log.d(TAG, "Drawable大小:$byteCount")
    }

    private fun loadXX() {
        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.big_picture_xx)
        val byteCount = decodeResource.allocationByteCount / 1024.0 / 1024.0
        Log.d(TAG, "DrawableXX大小:$byteCount")
    }

    /**
     * 尺寸压缩
     */
    private fun sizeCompress() {
        val bytes = assets.open("big_picture.jpg").readBytes()
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val outBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        Log.d(TAG, "尺寸压缩1/2:${outBitmap.allocationByteCount / 1024.0 / 1024.0}")
    }

    /**
     * 质量压缩
     * 它其实只能实现对file的影响，对加载这个图片出来的bitmap内存是无法节省的，还是那么大。
     * 因为bitmap在内存中的大小是按照像素计算的，也就是width*height，对于质量压缩，并不会改变图片的真实的像素
     */
    private fun qualityCompress() {
        val bytes = assets.open("big_picture.jpg").readBytes()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray = stream.toByteArray()
        val outBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        Log.d(TAG, "质量压缩50%:${outBitmap.allocationByteCount / 1024.0 / 1024.0}")
    }

    /**
     * RGB通道压缩
     */
    private fun rgbCompress() {
        val bytes = assets.open("big_picture.jpg").readBytes()
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        Log.d(TAG, "RGB通道压缩RGB_565:${bitmap.allocationByteCount / 1024.0 / 1024.0}")
    }

    private fun glideCompress() {
        val s = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                val width = resource.intrinsicWidth
                val height = resource.intrinsicHeight
                resource.setBounds(0, 0, width, height)
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                Log.d(TAG, "Glide加载:${bitmap.allocationByteCount / 1024.0 / 1024.0}")
                return false
            }
        }

        val bytes = assets.open("big_picture.jpg").readBytes()
        Glide.with(this).load(bytes).listener(s).into(img_view)

    }

    private fun showRegionImage() {
        val stream = assets.open("big_picture.jpg")
        val decoder = BitmapRegionDecoder.newInstance(stream, false)
        val options = BitmapFactory.Options()
        val bitmap = decoder.decodeRegion(Rect(0, 0, 200, 200), options)
        img_view.setImageBitmap(bitmap)
    }
}





