package com.konstant.tool.lite.module.setting.activity

import android.os.Bundle
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.setting.param.ThemeChanged
import kotlinx.android.synthetic.main.activity_theme.*
import org.greenrobot.eventbus.EventBus

/**
 * 描述:主题设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:09
 * 备注:
 */

class ThemeActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        selector_red.setOnClickListener(this)
        selector_class.setOnClickListener(this)
        selector_blue.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var thme = R.style.tool_lite_class
        when (v.id) {
            R.id.selector_red -> {
                thme = R.style.tool_lite_red
            }
            R.id.selector_class -> {
                thme = R.style.tool_lite_class
            }
            R.id.selector_blue -> {
                thme = R.style.tool_lite_blue
            }
        }
        SettingManager.saveTheme(this,thme)
        EventBus.getDefault().post(ThemeChanged())
    }
}
