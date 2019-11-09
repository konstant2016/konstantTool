package com.konstant.tool.lite.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.SwipeBackStatus
import com.konstant.tool.lite.base.UpdateManager
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.setting.activity.SettingActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_left.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述:主页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class MainActivity : BaseActivity() {

    private var mLastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("菜籽工具箱")
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        img_back.visibility = View.GONE
        img_more.visibility = View.GONE
        img_drawer.visibility = View.VISIBLE
        img_setting.visibility = View.VISIBLE

        val adapter = AdapterMainConfig(mConfig.configs)
        adapter.setOnItemClickListener { _, position ->
            val type = mConfig.configs[position].type
            startActivityWithType(type)
        }
        recycler_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setAdapter(adapter)
        }

        img_drawer.setOnClickListener { draw_layout.openDrawer(Gravity.LEFT) }

        img_setting.setOnClickListener { startActivity(SettingActivity::class.java) }

        if (SettingManager.getAutoCheckUpdate(this)) UpdateManager.autoCheckoutUpdate()

    }

    override fun onSwipeBackChanged(msg: SwipeBackStatus) {

    }

    override fun onBackPressed() {
        if (draw_layout.isDrawerOpen(layout_left)) {
            draw_layout.closeDrawer(Gravity.LEFT)
            return
        }
        if (SettingManager.getExitTipsStatus(this) and (System.currentTimeMillis() - mLastTime > 2000)) {
            mLastTime = System.currentTimeMillis()
            showToast("再按一次返回键退出应用")
            return
        }
        if (SettingManager.getKillProcess(this)) {
            finish()
            Handler().postDelayed({ android.os.Process.killProcess(android.os.Process.myPid()) }, 100)
        } else {
            Intent(Intent.ACTION_MAIN).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                addCategory(Intent.CATEGORY_HOME)
                startActivity(this)
            }
        }
    }
}
