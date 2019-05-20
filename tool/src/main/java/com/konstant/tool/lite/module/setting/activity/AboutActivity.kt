package com.konstant.tool.lite.module.setting.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.H5Activity
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
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        val info = packageManager.getPackageInfo(this.packageName, 0)
        tv_version.text = "版本号：${info.versionName}"

        tv_github.apply {
            paint.flags = Paint.UNDERLINE_TEXT_FLAG
            paint.isAntiAlias = true
            setOnClickListener {
                with(Intent(this@AboutActivity, H5Activity::class.java)) {
                    putExtra(H5Activity.H5_URL, "https://github.com/konstant2016/konstantTool")
                    startActivity(this)
                }
            }
        }
    }
}
