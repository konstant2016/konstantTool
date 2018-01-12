package com.konstant.konstanttools.ui.activity.toolactivity.qrcode

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import com.mylhyl.zxing.scanner.decode.QRDecode
import kotlinx.android.synthetic.main.activity_qrscan.*
import kotlinx.android.synthetic.main.title_layout.*


class QRScanActivity : BaseActivity() {

    private val PHOTO_PICK_CODE = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)
        setTitle("二维码扫描")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        initScanStyle()
        requestPermission()
        // 二维码扫描结果
        layout_scan.setOnScannerCompletionListener { rawResult, parsedResult, barcode ->
            if (rawResult == null) {
                Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show()
                return@setOnScannerCompletionListener
            }
            onScanResult(parsedResult.toString())
        }

        // 从本地选择图片
        img_more.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            startActivityForResult(intent, PHOTO_PICK_CODE)
        }

        // 打开闪光灯
        checkbox_light.setOnCheckedChangeListener { _, isChecked -> layout_scan.toggleLight(isChecked) }
    }

    // 初始化扫描框样式
    private fun initScanStyle() {
        layout_scan.setLaserFrameTopMargin(50)
    }

    // 请求权限
    private fun requestPermission() {
        requestPermission(Manifest.permission.CAMERA, "需要拍照权限以扫描二维码")
    }

    // 权限申请结果
    override fun onPermissionResult(result: Boolean) {
        super.onPermissionResult(result)
        layout_scan.restartPreviewAfterDelay(500)
    }

    // 扫描结果弹窗
    private fun onScanResult(content: String) {
        layout_scan.onPause()
        layout_scan_result.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top))
        layout_scan_result.visibility = View.VISIBLE
        tv_scan_result.text = content
    }

    // 返回键按下后执行的代码
    override fun onBackPressed() {
        if (layout_scan_result.visibility == View.VISIBLE) {
            layout_scan_result.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_to_bottom))
            layout_scan_result.visibility = View.GONE
            layout_scan.onResume()
        } else {
            super.onBackPressed()
        }
    }

    // 读取本地图片返回结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_PICK_CODE){
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data?.data)
            QRDecode.decodeQR(bitmap){rawResult, parsedResult, _ ->
                if (rawResult == null) {
                    Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show()
                    return@decodeQR
                }
                onScanResult(parsedResult.toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        layout_scan.onPause()
    }

    override fun onResume() {
        super.onResume()
        layout_scan.onResume()
    }

}
