package com.konstant.tool.lite.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import com.konstant.tool.lite.module.setting.SettingManager
import java.io.File

class ImageSelector : AppCompatActivity() {

    companion object {

        private val CAMERA_REQUEST = 1      // 拍照
        private val PHOTO_REQUEST = 2       // 相册
        private val PHOTO_CLIP = 3          // 裁剪

        private lateinit var mFileName: String

        lateinit var mCallback: ((result: Boolean) -> Unit)
        fun selectImg(context: Context, name: String, callback: (result: Boolean) -> Unit) {
            mCallback = callback
            mFileName = name
            with(Intent(context, ImageSelector::class.java)) {
                putExtra("type", PHOTO_REQUEST)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }

        fun takePhoto(context: Activity, name: String, callback: (result: Boolean) -> Unit) {
            mCallback = callback
            mFileName = name
            with(Intent(context, ImageSelector::class.java)) {
                putExtra("type", CAMERA_REQUEST)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = intent.getIntExtra("type", 0)
        when (type) {
            CAMERA_REQUEST -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val file = File(externalCacheDir, mFileName)
                val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, CAMERA_REQUEST)
            }
            PHOTO_REQUEST -> {
                with(Intent(Intent.ACTION_PICK)) {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    startActivityForResult(this, PHOTO_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            mCallback.invoke(false)
            finish()
        }
        when (requestCode) {
            CAMERA_REQUEST -> {
                val file = File(externalCacheDir, mFileName)
                if (!file.exists()) return
                val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
                clipPhoto(uri)
            }
            PHOTO_REQUEST -> {
                data?.let {
                    clipPhoto(it.data)
                }
            }
            PHOTO_CLIP -> {
                mCallback.invoke(true)
                finish()
            }
            else -> {
                mCallback.invoke(false)
                finish()
            }
        }
    }

    // 调用系统中自带的图片剪裁
    private fun clipPhoto(uri: Uri) {
        val cropPhoto = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), SettingManager.NAME_HEADER_THUMB)
        with(Intent("com.android.camera.action.CROP")) {
            setDataAndType(uri, "image/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            putExtra("crop", "true")
            putExtra("scale", true)

            putExtra("aspectX", 1)
            putExtra("aspectY", 1)

            //输出的宽高
            putExtra("outputX", 300)
            putExtra("outputY", 300)

            putExtra("return-data", false)
            putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropPhoto))
            putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            putExtra("noFaceDetection", true) // no face detection
            startActivityForResult(this, PHOTO_CLIP)
        }
    }
}
