package com.konstant.tool.lite.module.beauty.adapter

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bm.library.PhotoView
import com.konstant.tool.lite.module.beauty.activity.LookPictureActivity
import com.konstant.tool.lite.util.FileUtil
import com.konstant.tool.lite.view.KonstantDialog
import com.squareup.picasso.Picasso
import com.yanzhenjie.permission.AndPermission

/**
 * 描述:看图页面的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/24 下午4:13
 * 备注:
 */

class AdapterLookPicture(val context: LookPictureActivity, urlList: List<String>) : PagerAdapter() {

    private val imgList = ArrayList<ImageView>()

    init {
        urlList.forEach {
            val photoView = PhotoView(context)
            photoView.enable()
            photoView.scaleType = ImageView.ScaleType.FIT_CENTER
            Picasso.with(context).load(it).into(photoView)
            imgList.add(photoView)
        }
    }

    override fun isViewFromObject(view: View?, any: Any?): Boolean = (view === any)


    override fun getCount(): Int = imgList.size


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val image = imgList[position]
        container?.addView(image)
        image.setOnClickListener { context.finish() }
        image.setOnLongClickListener {
            KonstantDialog(context)
                    .setMessage("是否要保存到本地？")
                    .setPositiveListener {
                        it.dismiss()
                        requestPermission((image.drawable as BitmapDrawable).bitmap)
                    }
                    .createDialog()
            true
        }
        return image
    }

    override fun destroyItem(container: ViewGroup?, position: Int, any: Any?) {
        container?.removeView(any as View)
    }

    // 请求权限
    private fun requestPermission(bitmap: Bitmap) {
        AndPermission.with(context)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onDenied {
                    context.showToast("您拒绝了本地读取权限")
                }
                .onGranted {
                    savePicture(bitmap)
                }
                .start()
    }

    // 保存图片
    private fun savePicture(bitmap: Bitmap) {
        if (FileUtil.saveBitmapToAlbum(bitmap = bitmap, name = "${System.currentTimeMillis()}.jpg")) {
            context.showToast("保存成功")
        } else {
            context.showToast("保存失败")
        }
    }
}