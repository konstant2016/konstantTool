package com.konstant.tool.lite.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.*
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.setting.activity.SettingActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_viewpager.*
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
    private val mFragmentList = ArrayList<BaseFragment>()
    private val mAdapter by lazy { BaseFragmentAdapter(supportFragmentManager, mFragmentList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        img_back.visibility = View.GONE
        img_more.visibility = View.GONE
        img_drawer.visibility = View.VISIBLE
        img_setting.visibility = View.VISIBLE

        view_pager.adapter = mAdapter
        view_segment.setUpWithViewPager(view_pager)

        img_drawer.setOnClickListener { draw_layout.openDrawer(Gravity.LEFT) }

        img_setting.setOnClickListener { startActivity(SettingActivity::class.java) }

        if (SettingManager.getAutoCheckUpdate(this)) UpdateManager.autoCheckoutUpdate()

    }

    override fun onPause() {
        super.onPause()
        FunctionCollectorManager.onDestroy(this)
    }

    override fun onResume() {
        super.onResume()
        mFragmentList.clear()
        if (SettingManager.getShowCollection(this) && FunctionCollectorManager.getCollectionFunction().isNotEmpty()) {
            mFragmentList.add(FunctionCollectionFragment())
            mFragmentList.add(AllFunctionFragment())
            setSegmentalTitle(getString(R.string.main_my_collection), getString(R.string.main_all_function))
        } else {
            mFragmentList.add(AllFunctionFragment())
            setTitle(getString(R.string.app_name))
        }
        mAdapter.notifyDataSetChanged()
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
            showToast(getString(R.string.main_exit_toast))
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
