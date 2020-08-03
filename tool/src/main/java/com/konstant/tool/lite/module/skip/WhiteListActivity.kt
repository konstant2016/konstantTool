package com.konstant.tool.lite.module.skip

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.AppUtil
import kotlinx.android.synthetic.main.activity_white_list.*
import kotlin.concurrent.thread

class WhiteListActivity : BaseActivity() {

    private val mAppList = mutableListOf<AppDataWrapper>()
    private val mAdapter by lazy { AdapterWhiteList(mAppList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_list)
        init()
        getAppDataList()
    }

    private fun init() {
        recycler_package.layoutManager = LinearLayoutManager(this)
        recycler_package.adapter = mAdapter
    }

    private fun getAppDataList() {
        showLoading(true)
        thread {
            val list = AppUtil.getUserAppList()
                    .map {
                        val icon = it.loadIcon(this.packageManager)
                        val packageName = it.activityInfo.packageName
                        val appName = it.loadLabel(this.packageManager).toString()
                        val checked = AutoSkipManager.getAppWhiteList().any { it == packageName }
                        AppDataWrapper(checked, appName, packageName, icon)
                    }
            mAppList.clear()
            mAppList.addAll(list)
            runOnUiThread {
                mAdapter.notifyDataSetChanged()
                showLoading(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val list = mAppList.filter { it.isChecked }.map { it.packageName }
        AutoSkipManager.addAppIntoWhiteList(list)
        AutoSkipManager.onDestroy(this)
    }
}