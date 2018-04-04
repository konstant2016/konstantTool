package com.konstant.toollite.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.eventbusparam.SwipeBackState
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle("设置")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        layout_theme.setOnClickListener { startActivity(Intent(this, ThemeActivity::class.java)) }

        val state = FileUtils.readDataWithSharedPreference(this, Constant.NAME_SWIPEBACK_STATE, false)
        btn_switch.isChecked = state

        btn_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AlertDialog.Builder(this)
                        .setTitle("注意")
                        .setMessage("开启滑动返回后，切换主题时，可能会有闪屏情况出现,确认开启？")
                        .setPositiveButton("确定") { dialog, _ ->
                            EventBus.getDefault().post(SwipeBackState(true))
                            FileUtils.saveDataWithSharedPreference(this, Constant.NAME_SWIPEBACK_STATE, true)
                            dialog.dismiss()
                        }
                        .setNegativeButton("取消") { dialog, _ ->
                            dialog.dismiss()
                            btn_switch.isChecked = false
                        }.create().show()
            } else {
                EventBus.getDefault().post(SwipeBackState(false))
                FileUtils.saveDataWithSharedPreference(this, Constant.NAME_SWIPEBACK_STATE, false)
            }
        }
    }
}
