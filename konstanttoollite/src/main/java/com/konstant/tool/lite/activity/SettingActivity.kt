package com.konstant.tool.lite.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
            camera()
        }
        // 相册
        view.findViewById(R.id.text_photo).setOnClickListener {
            dialog.dismiss()
            photo()
        }
        // 恢复默认
        view.findViewById(R.id.text_default).setOnClickListener {
            dialog.dismiss()
            default()
        }

        dialog.hideNavigation()
                .addView(view)
                .createDialog()
    }


    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), NameConstant.NAME_USER_HEADER_PIC_NAME)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        startActivityForResult(intent, CAMERA_REQUEST)
    }

    private fun photo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, PHOTO_REQUEST)
    }

    private fun default() {
        File(externalCacheDir, NameConstant.NAME_USER_HEADER_PIC_NAME).delete()
        EventBus.getDefault().post(UserHeaderChanged())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            CAMERA_REQUEST -> {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), NameConstant.NAME_USER_HEADER_PIC_NAME)
                if (!file.exists()) return
                clipPhoto(Uri.fromFile(file))
            }
            PHOTO_REQUEST -> {
                data?.let { clipPhoto(data.data) }
            }
            PHOTO_CLIP -> {
                if (data?.extras == null) return
                val bitmap = data.extras.getParcelable<Bitmap>("data")
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                FileUtils.saveFileToInnerStorage(this, NameConstant.NAME_USER_HEADER_PIC_NAME_THUMB, stream.toByteArray())
                EventBus.getDefault().post(UserHeaderChanged())
            }
        }
    }

    // 调用系统中自带的图片剪裁
    private fun clipPhoto(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 250)
        intent.putExtra("outputY", 250)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, PHOTO_CLIP)
    }
}
