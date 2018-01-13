package com.konstant.konstanttools.ui.activity.toolactivity.qrcode

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.widget.Toast
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import com.mylhyl.zxing.scanner.encode.QREncode
import kotlinx.android.synthetic.main.activity_qrcode.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class QRCodeActivity : BaseActivity() {

    private var mBitmap:Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        setTitle("二维码生成与扫描")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        root_layout_qrcode.setOnClickListener { hideSoftKeyboard() }

        // 生成二维码
        btn_create.setOnClickListener {

            if(TextUtils.isEmpty(et_qr.text)){
                Toast.makeText(this, "记得输入内容哦", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mBitmap = QREncode.Builder(this)
                    .setColor(Color.BLACK)
                    .setMargin(2)
                    .setContents(et_qr.text.toString())
                    .setSize(1000)
                    .build().encodeAsBitmap()
            img_result.setImageBitmap(mBitmap)
        }

        // 跳转到二维码扫描界面
        btn_scane.setOnClickListener { startActivity(Intent(this,QRScanActivity::class.java)) }

        // 长按二维码，弹窗询问是否保存到本地
        img_result.setOnLongClickListener {
            if(mBitmap == null) return@setOnLongClickListener true

            AlertDialog.Builder(this)
                    .setMessage("是否要保存到本地？")
                    .setNegativeButton("取消"){dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("确定"){dialog, _ ->
                        dialog.dismiss()
                        requestWritePermission()
                    }
                    .create().show()
            true
        }

    }

    // 写出文件到本地
    private fun writeToStroage(bitmap: Bitmap) {
            val fileParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val name = "${System.currentTimeMillis()}.jpg"
            val file = File(fileParent, name)
            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
                outputStream.flush()
                outputStream.close()
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    // 请求权限
    private fun requestWritePermission(){
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,"需要存储权限以保存二维码图片")
    }

    // 权限申请结果
    override fun onPermissionResult(result: Boolean) {
        super.onPermissionResult(result)
        if(result) writeToStroage(mBitmap!!) else Toast.makeText(this, "您拒绝了权限申请", Toast.LENGTH_SHORT).show()
    }
}
