package com.konstant.toollite.activity

import android.os.Bundle
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity

/**
 * 描述:关于页面
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setTitle("关于")
    }
}
