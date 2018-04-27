package com.konstant.tool.lite.activity

import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.SettingManager
import com.konstant.tool.lite.eventbusparam.SwipeBackState
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus

/**
 * 描述:APP设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

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

        btn_switch.isChecked = SettingManager.getSwipeBackState(this)

        btn_switch.setOnCheckedChangeListener { _, isChecked ->
            EventBus.getDefault().post(SwipeBackState(isChecked))
            SettingManager.setSwipeBackState(this,isChecked)
        }

        layout_swipe.setOnClickListener {
            btn_switch.isChecked = !btn_switch.isChecked
        }

        layout_about.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
    }
}
