package com.konstant.tool.lite.activity

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

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
        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        tv_version.text = "版本号：${info.versionName}"
    }
}
