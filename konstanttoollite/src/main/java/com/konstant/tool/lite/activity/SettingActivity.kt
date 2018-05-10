package com.konstant.tool.lite.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.NameConstant
import com.konstant.tool.lite.data.SettingManager
import com.konstant.tool.lite.eventbusparam.SwipeBackState
import com.konstant.tool.lite.eventbusparam.UserHeaderChanged
import com.konstant.tool.lite.util.FileUtils
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


/**
 * 描述:APP设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class SettingActivity : BaseActivity() {

    private val CAMERA_REQUEST = 1      // 拍照
    private val PHOTO_REQUEST = 2       // 相册
    private val PHOTO_CLIP = 3          // 裁剪

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle("设置")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        layout_theme.setOnClickListener { startActivity(Intent(this, ThemeActivity::class.java)) }

        btn_switch.isChecked = SettingManager.getSwipeBackState(this)

        btn_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSwitchEnable(isChecked)
            }
            EventBus.getDefault().post(SwipeBackState(isChecked))
            SettingManager.setSwipeBackState(this, isChecked)
        }

        layout_swipe.setOnClickListener {
            btn_switch.isChecked = !btn_switch.isChecked
        }

        layout_about.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }

        layout_header.setOnClickListener { headerSelector() }
    }

    private fun onSwitchEnable(isChecked: Boolean) {
        val dialog = KonstantDialog(this)
        dialog.setOnDismissListener {
            if (isChecked) btn_switch.isChecked = false
        }
        dialog.setMessage("开启滑动返回后，侧边栏将只能通过主页打开，确认开启？")
                // 取消
                .setNegativeListener {
                    it.dismiss()
                    btn_switch.isChecked = false
                }
                // 确认
                .setPositiveListener {
                    it.dismiss()
                    btn_switch.isChecked = true
                }
                .createDialog()
    }

    // 头像选择
    private fun headerSelector() {
        val dialog = KonstantDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_header_selector, null)
        // 拍照
        view.findViewById(R.id.text_camera).setOnClickListener {
            dialog.dismiss()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val file = File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME)
            val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, CAMERA_REQUEST)
        }
        // 相册
        view.findViewById(R.id.text_photo).setOnClickListener {
            dialog.dismiss()
            with(Intent(Intent.ACTION_PICK)) {
                setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(this, PHOTO_REQUEST)
            }
        }
        // 恢复默认
        view.findViewById(R.id.text_default).setOnClickListener {
            dialog.dismiss()
            File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME_THUMB).delete()
            EventBus.getDefault().post(UserHeaderChanged())
        }
        // 显示dialog
        dialog.hideNavigation()
                .addView(view)
                .createDialog()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            CAMERA_REQUEST -> {
                val file = File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME)
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
                EventBus.getDefault().post(UserHeaderChanged())
            }
        }
    }

    // 调用系统中自带的图片剪裁
    private fun clipPhoto(uri: Uri) {
        val cropPhoto = File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME_THUMB)
        if (cropPhoto.exists()) {
            cropPhoto.delete()
            cropPhoto.createNewFile()
        }
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
