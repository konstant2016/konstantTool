package com.konstant.tool.lite.adapter

import android.os.Environment
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bm.library.PhotoView
import com.konstant.tool.lite.activity.LookPictureActivity
import com.konstant.tool.lite.server.net.NetworkUtil
import com.konstant.tool.lite.view.KonstantDialog
import com.squareup.picasso.Picasso
import com.yanzhenjie.permission.AndPermission
import java.io.File
import java.io.FileOutputStream

/**
 * 描述:看图页面的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/24 下午4:13
 * 备注:
 */

class AdapterLookPicture(val context: LookPictureActivity, val urlList: List<String>) : PagerAdapter() {

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
                        requestPermission(position)
                    }
            true
        }
        return image
    }

    override fun destroyItem(container: ViewGroup?, position: Int, any: Any?) {
        container?.removeView(any as View)
    }

    // 请求权限
    private fun requestPermission(position: Int) {
        AndPermission.with(context)
                .onDenied {
                    Toast.makeText(context, "您拒绝了本地读取权限", Toast.LENGTH_SHORT).show()
                }
                .onGranted {
                    savePicture(position)
                }
                .start()
    }

    // 保存图片
    private fun savePicture(position: Int) {
        NetworkUtil.get(urlList[position], "") { state, data ->
            if (!state) {
                showToast("获取数据失败")
                return@get
            }
            val name = urlList[position].substring(urlList[position].lastIndexOf("/") + 1)
            val fileParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(fileParent, name)
            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(data)
                outputStream.flush()
                outputStream.close()
                showToast("保存成功")
            } catch (e: Exception) {
                showToast("保存失败")
            }
        }
    }

    // 展示吐司
    private fun showToast(string: String) {
        context.runOnUiThread {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }
    }

}