package com.konstant.tool.lite.module.ruler

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.StatusBarUtil

/**
 * 描述:直尺
 * 创建人:菜籽
 * 创建时间:2018/5/8 下午7:08
 * 备注:
 */

class RulerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setFullScreen(this)
        StatusBarUtil.setCutoutMode(this)
        setContentView(R.layout.activity_ruler)
        setSwipeBackEnable(false)
        showTitleBar(false)
        setDrawerLayoutStatus(false)
    }
}
