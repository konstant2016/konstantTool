package com.konstant.toollite.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bm.library.PhotoView
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.util.Utils
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList

class LookPictureActivity : BaseActivity() {

    private var mViewPager: ViewPager? = null
    private var mAdapter: ViewPagerAdapter? = null
    private val urlList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_picture)
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mViewPager = findViewById(R.id.view_pager) as ViewPager

        val intent = intent
        urlList.addAll(intent.getStringArrayListExtra("urlList"))
        Log.i("连接集合", urlList.toString())

        val imageViewList = ArrayList<ImageView>()
        for (i in urlList.indices) {
            val photoView = PhotoView(this)
            photoView.enable()
            photoView.scaleType = ImageView.ScaleType.FIT_CENTER
            Picasso.with(this).load(urlList[i]).into(photoView)
            imageViewList.add(photoView)
        }


        mAdapter = ViewPagerAdapter(imageViewList, this)
        mViewPager!!.adapter = mAdapter

        val index = intent.getIntExtra("index", 0)
        mViewPager!!.currentItem = index

    }

    override fun onPermissionResult(result: Boolean) {
        super.onPermissionResult(result)
        if (result) {
            writeToStorage(urlList[mViewPager!!.currentItem])
        } else {
            Toast.makeText(this, "您拒绝了SD卡读写权限", Toast.LENGTH_SHORT).show()
        }
    }

    //写出文件到本地
    fun writeToStorage(urlString: String) {
        Thread {
            val byteArray = Utils.getByteArray(urlString)
            val fileParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val name = urlString.substring(urlString.lastIndexOf("/") + 1)
            val file = File(fileParent, name)
            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(byteArray!!, 0, byteArray!!.size)
                outputStream.flush()
                outputStream.close()
                showToast("保存成功")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                showToast("保存失败")
            } catch (e: IOException) {
                e.printStackTrace()
                showToast("保存失败")
            }
        }.start()
    }

    // 更新界面提示
    private fun showToast(text: String) {
        runOnUiThread { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
    }

    // viewpager的适配器
    internal class ViewPagerAdapter(private val imageViewList: List<ImageView>, private val context: Context) : PagerAdapter() {

        override fun getCount(): Int {
            return imageViewList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val imageView = imageViewList[position]

            container.addView(imageView)

            imageView.setOnClickListener {  (context as Activity).finish() }

            imageView.setOnLongClickListener {
                showDialog()
                true
            }

            return imageView

        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }


        @TargetApi(Build.VERSION_CODES.M)
        private fun showDialog() {
            AlertDialog.Builder(context).setMessage("是否要保存到本地?")
                    .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton("确定") { dialog, _ ->
                        dialog.dismiss()
                        (context as LookPictureActivity).requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "需要手机读写权限用以保存图片")
                    }
                    .create().show()
        }


    }
}
