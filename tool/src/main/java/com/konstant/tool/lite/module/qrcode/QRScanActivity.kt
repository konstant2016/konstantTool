package com.konstant.tool.lite.module.qrcode

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.mylhyl.zxing.scanner.decode.QRDecode
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_qrscan.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述:二维码扫描
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

@SuppressLint("MissingSuperCall")
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
        layout_scan.setOnScannerCompletionListener { rawResult, parsedResult, _ ->
            if (rawResult == null) {
                showToast("未发现二维码")
                return@setOnScannerCompletionListener
            }
            onScanResult(parsedResult.toString())
        }

        // 从本地选择图片
        img_more.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
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
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA)
                .onGranted {
                    layout_scan.restartPreviewAfterDelay(500)
                    layout_scan.onResume()
                }
                .onDenied { showToast("需要摄像头权限用以扫描二维码") }
                .start()
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
        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_PICK_CODE) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
            QRDecode.decodeQR(bitmap) { rawResult, parsedResult, _ ->
                if (rawResult == null) {
                    showToast("未发现二维码")
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
