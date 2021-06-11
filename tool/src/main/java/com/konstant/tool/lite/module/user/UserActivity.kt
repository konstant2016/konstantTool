package com.konstant.tool.lite.module.user

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity

/**
 * 时间：6/11/21 3:05 PM
 * 作者：吕卡
 * 备注：登录和注册页面
 */

class UserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}