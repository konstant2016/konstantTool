package com.konstant.tool.lite.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.io.File

/**
 * 时间：2019/5/22 10:49
 * 创建：菜籽
 * 描述：封装的图片选择器，包含从系统图库获取图片以及从相机获取图片
 *      本选择器支持安卓4.0以上版本，同时针对安卓8.0的新特性做了适配工作
 *      本质上是启动了一个完全透明的activity，在内部对startActivityForResult进行了封装处理
 *
 * 接口如下：
 *      通过伴生接口，提供给外部的接口有三个：
 *      1、fun selectImg(context: Context, name: String, callback: (result: Boolean) -> Unit)
 *      2、fun takePhoto(context: Context, name: String, callback: (result: Boolean) -> Unit)
 *      3、fun clipPhoto(context: Context, name: String, callback: (result: Boolean) -> Unit)
 *      获取的图片一律存放在/storage/emulated/0/Android/包名/files/Pictures 下
 *      当接口回调为true时，表示图片已处理完毕，调用者可根据传过来的文件名字获取图片
 *      当接口回调false时，基本上用户手动取消了操作，调用者可根据此结果做响应处理
 */

class ImageSelector : AppCompatActivity() {

    private val TAG = "ImageSelector"

    companion object {

        private val CAMERA_REQUEST = 1      // 拍照
        private val PHOTO_REQUEST = 2       // 相册
        private val PHOTO_CLIP = 3          // 裁剪

        private lateinit var mFileName: String

        lateinit var mCallback: ((result: Boolean) -> Unit)

        //  从相册选择图片，提供给外部的接口
        fun selectImg(context: Context, name: String, width: Int = 300, height: Int = 300, callback: (result: Boolean) -> Unit) {
            mCallback = callback
            mFileName = name
            with(Intent(context, ImageSelector::class.java)) {
                putExtra("type", PHOTO_REQUEST)
                putExtra("width", width)
                putExtra("height", height)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }

        //  调用摄像机拍摄图片，提供给外部的接口
        fun takePhoto(context: Context, name: String, width: Int = 300, height: Int = 300, callback: (result: Boolean) -> Unit) {
            mCallback = callback
            mFileName = name
            with(Intent(context, ImageSelector::class.java)) {
                putExtra("type", CAMERA_REQUEST)
                putExtra("width", width)
                putExtra("height", height)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }

        // 单独的调用系统裁剪图片的接口，这个接口一般很少单独使用，提供给外部的接口
        fun clipPhoto(context: Context, name: String, callback: (result: Boolean) -> Unit) {
            mCallback = callback
            mFileName = name
            with(Intent(context, ImageSelector::class.java)) {
                putExtra("type", PHOTO_CLIP)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    private val mWidth by lazy { intent.getIntExtra("width", 0) }
    private val mHeight by lazy { intent.getIntExtra("height", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invasionStatusBar(this)
        val type = intent.getIntExtra("type", 0)
        when (type) {
            CAMERA_REQUEST -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val uri = FileUtil.createUriWithFile(this, mFileName)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, CAMERA_REQUEST)
            }
            PHOTO_REQUEST -> {
                with(Intent(Intent.ACTION_PICK)) {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    startActivityForResult(this, PHOTO_REQUEST)
                }
            }
            PHOTO_CLIP -> {
                val uri = FileUtil.createUriWithFile(this, mFileName)
                clipPhoto(uri)
            }
        }
    }

    // 本activity调用系统的API获取数据后得到的结果，并对结果进行处理
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "requestCode" + requestCode)
        when (requestCode) {
            CAMERA_REQUEST -> {
                val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mFileName)
                if (!file.exists()) {
                    mCallback.invoke(false)
                    finish()
                    return
                }
                val uri = FileUtil.createUriWithFile(this, mFileName)
                clipPhoto(uri)
            }
            PHOTO_REQUEST -> {
                if (data != null) {
                    clipPhoto(data.data!!)
                } else {
                    mCallback.invoke(false)
                    finish()
                }
            }
            PHOTO_CLIP -> {
                Log.d(TAG, "data empty ?:${data?.data == null}")
                mCallback.invoke(resultCode == RESULT_OK)
                finish()
            }
            else -> {
                finish()
            }
        }
    }

    // 调用系统中自带的图片剪裁
    private fun clipPhoto(uri: Uri) {
        val width = if (mWidth == 0) 300 else mWidth
        val height = if (mHeight == 0) 300 else mHeight
        val outUri = FileUtil.createUriWithFile(this, mFileName)
        grantCropPermission(outUri)
        with(Intent("com.android.camera.action.CROP")) {
            setDataAndType(uri, "image/*")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            putExtra("crop", "true")
            putExtra("scale", true)

            putExtra("aspectX", width)
            putExtra("aspectY", height)

            //输出的宽高
            putExtra("outputX", width)
            putExtra("outputY", height)

            putExtra("return-data", false)
            putExtra(MediaStore.EXTRA_OUTPUT, outUri)
            putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            putExtra("noFaceDetection", true) // no face detection
            startActivityForResult(this, PHOTO_CLIP)
        }
    }

    /**
     * 对Uri进行临时授权，使它可以写入到应用内部目录中
     */
    private fun grantCropPermission(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.type = "image/*"
        val infoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        infoList.forEach {
            val packageName = it.activityInfo.packageName
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    // 导航栏隐藏，状态栏全透明
    private fun invasionStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val decorView = window.decorView
            decorView.systemUiVisibility = (decorView.systemUiVisibility
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
