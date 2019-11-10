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
        setTitle(getString(R.string.concentration_title))
    }

    override fun initBaseViews() {
        super.initBaseViews()
        view_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_progress.text = "${getString(R.string.concentration_set_count_down_time)}(${getString(R.string.concentration_current)}${progress + 5}${getString(R.string.concentration_minute)})"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        btn_start.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                KonstantDialog(this)
                        .setTitle(getString(R.string.concentration_need_permission))
                        .setMessage(getString(R.string.concentration_permission_message))
                        .setNegativeListener { showToast(getString(R.string.base_permission_cancel)) }
                        .setPositiveListener {
                            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${Uri.parse(packageName)}"))
                            startActivity(intent)
                            it.dismiss()
                        }
                        .createDialog()
                return@setOnClickListener
            }
            KonstantDialog(this)
                    .setTitle(getString(R.string.concentration_alert_again))
                    .setMessage(getString(R.string.concentration_alert_again_message))
                    .setPositiveListener {
                        ConcentrationService.startCountDown(this, view_seekbar.progress + 5)
                        it.dismiss()
                    }
                    .createDialog()
        }
    }
}
