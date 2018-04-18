package com.konstant.tool.lite.activity

import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.eventbusparam.SwipeBackState
import com.konstant.tool.lite.util.NameConstant
import com.konstant.tool.lite.util.FileUtils
import com.konstant.tool.lite.view.KonstantDialog
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

        val state = FileUtils.readDataWithSharedPreference(this, NameConstant.NAME_SWIPEBACK_STATE, false)
        btn_switch.isChecked = state

        btn_switch.setOnCheckedChangeListener { _, isChecked ->
            EventBus.getDefault().post(SwipeBackState(isChecked))
            FileUtils.saveDataWithSharedPreference(this, NameConstant.NAME_SWIPEBACK_STATE, isChecked)
        }

        layout_about.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
    }
}
