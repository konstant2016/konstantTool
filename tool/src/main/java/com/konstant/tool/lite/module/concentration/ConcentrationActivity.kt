package com.konstant.tool.lite.module.concentration

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_concentration.*

/**
 * 作者：konstant
 * 时间：2019/10/29 19:53
 * 描述：专注模式
 */

class ConcentrationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concentration)
        initBaseViews()
        setTitle("专注模式")
    }

    override fun initBaseViews() {
        super.initBaseViews()
        view_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_progress.text = "设置倒计时时间(当前${progress + 5}分钟)"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        btn_start.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                KonstantDialog(this)
                        .setTitle("需要申请额外权限")
                        .setMessage("请在下一个页面中开启'显示在其他应用的上层'权限开关")
                        .setNegativeListener { showToast("授权已取消") }
                        .setPositiveListener {
                            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${Uri.parse(packageName)}"))
                            startActivity(intent)
                            it.dismiss()
                        }
                        .createDialog()
                return@setOnClickListener
            }
            KonstantDialog(this)
                    .setTitle("再次提示")
                    .setMessage("专注模式开启后在倒计时结束前无法关闭，如需强制关闭，请长按电源键10秒以上以重启手机")
                    .setPositiveListener {
                        SettingManager.saveKillProcess(this, false)
                        ConcentrationService.startCountDown(this, view_seekbar.progress + 5)
                        it.dismiss()
                    }
                    .createDialog()
        }
    }
}
