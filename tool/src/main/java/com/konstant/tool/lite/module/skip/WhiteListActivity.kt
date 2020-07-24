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
    private val mAdapter by lazy { WhiteListAdapter(mAppList) }

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
            val list = AppUtil.getPackageInfoList()
                    .map { packageInfo ->
                        val icon = AppUtil.getAppIcon(packageInfo)
                        val appName = AppUtil.getAppName(packageInfo)
                        val packageName = AppUtil.getPackageName(packageInfo)
                        val checked = AutoSkipManager.getAppWhiteList().any { it == packageInfo.packageName }
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