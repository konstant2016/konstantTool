package com.konstant.tool.lite.activity

import android.os.Bundle
import android.view.WindowManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity

/**
 * 描述:直尺
 * 创建人:菜籽
 * 创建时间:2018/5/8 下午7:08
 * 备注:
 */

class RulerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ruler)
        setSwipeBackEnable(false)
    }
}
