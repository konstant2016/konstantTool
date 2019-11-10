package com.konstant.tool.lite.module.qrcode

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.FileUtil
import com.konstant.tool.lite.util.PermissionRequester
import com.konstant.tool.lite.view.KonstantDialog
import com.mylhyl.zxing.scanner.encode.QREncode
import kotlinx.android.synthetic.main.activity_qrcode.*

/**
 * 描述:二维码生成
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class QRCodeActivity : BaseActivity() {

    private var mBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        setTitle(getString(R.string.qrcode_title))
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        root_layout_qrcode.setOnClickListener { hideSoftKeyboard() }

        // 生成二维码
        btn_create.setOnClickListener {

            if (TextUtils.isEmpty(et_qr.text)) {
                showToast(getString(R.string.base_input_empty_toast))
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
        btn_scane.setOnClickListener { startActivity(Intent(this, QRScanActivity::class.java)) }

        // 长按二维码，弹窗询问是否保存到本地
        img_result.setOnLongClickListener {
            if (mBitmap == null) return@setOnLongClickListener true

            KonstantDialog(this)
                    .setMessage(getString(R.string.qrcode_save_to_local))
                    .setPositiveListener {
                        it.dismiss()
                        requestWritePermission()
                    }
                    .createDialog()
            true
        }
    }

    // 写出文件到本地
    private fun writeToStorage(bitmap: Bitmap?) {
        bitmap.let {
            val name = "${System.currentTimeMillis()}.jpg"
            val result = FileUtil.saveBitmapToAlbum(bitmap = bitmap, name = name)
            showToast(if (result) getString(R.string.qrcode_save_success) else getString(R.string.qrcode_save_fail))
        }
    }

    // 请求权限
    private fun requestWritePermission() {
        PermissionRequester.requestPermission(this,
                mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                { writeToStorage(mBitmap) },
                { showToast(getString(R.string.qrcode_permission_cancel)) })
    }

}
