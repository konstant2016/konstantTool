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
                onSwitchEnable()
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

    private fun onSwitchEnable() {
        KonstantDialog(this)
                .setMessage("开启滑动返回后，侧边栏将只能通过主页打开，确认开启？")
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
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, PHOTO_REQUEST)
        }
        // 恢复默认
        view.findViewById(R.id.text_default).setOnClickListener {
            dialog.dismiss()
            File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME_THUMB).delete()
            EventBus.getDefault().post(UserHeaderChanged())
        }

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
        try {
            if (cropPhoto.exists()) {
                cropPhoto.delete()
            }
            cropPhoto.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val cropImageUri = Uri.fromFile(cropPhoto)
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)

        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        //输出的宽高

        intent.putExtra("outputX", 300)
        intent.putExtra("outputY", 300)

        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        startActivityForResult(intent, PHOTO_CLIP)
    }
}
