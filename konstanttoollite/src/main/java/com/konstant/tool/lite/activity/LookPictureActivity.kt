package com.konstant.tool.lite.activity

import android.annotation.TargetApi
import android.app.Activity
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
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.Utils
import com.konstant.tool.lite.view.KonstantDialog
import com.squareup.picasso.Picasso
import com.yanzhenjie.permission.AndPermission
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * 描述:看图的详情页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class LookPictureActivity : BaseActivity() {

    private var mViewPager: ViewPager? = null
    private var mAdapter: ViewPagerAdapter? = null
    private val urlList = ArrayList<String>()

    private val mRequestCode = 16

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


    // 保存到本地
    private fun savePicture(){
        AndPermission.with(this)
                .onDenied {
                    Toast.makeText(this, "您拒绝了本地读取权限", Toast.LENGTH_SHORT).show()
                }
                .onGranted {
                    writeToStorage(urlList[mViewPager!!.currentItem])
                }
                .start()
    }

    //写出文件到本地
    private fun writeToStorage(urlString: String) {
        Thread {
            val byteArray = Utils.getByteArray(urlString)
            val fileParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val name = urlString.substring(urlString.lastIndexOf("/") + 1)
            val file = File(fileParent, name)
            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(byteArray, 0, byteArray.size)
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
            KonstantDialog(context as Activity)
                    .setMessage("是否要保存到本地?")
                    .setPositiveListener {
                        it.dismiss()
                        (context as LookPictureActivity).savePicture()
                    }
                    .createDialog()
        }
    }
}
