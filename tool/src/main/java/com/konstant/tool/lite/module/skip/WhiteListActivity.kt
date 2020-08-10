package com.konstant.tool.lite.module.skip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.AppUtil
import kotlinx.android.synthetic.main.activity_white_list.package_filter
import kotlinx.android.synthetic.main.activity_white_list.recycler_package
import kotlin.concurrent.thread

class WhiteListActivity : BaseActivity() {

    private val mAppList = ArrayList<AppDataWrapper>()
    private val mFilterList = ArrayList<AppDataWrapper>()
    private val mAdapter by lazy { AdapterWhiteList(mFilterList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_list)
        setTitle(getString(R.string.skip_white_list))
        init()
        getAppDataList()
    }

    private fun init() {
        recycler_package.layoutManager = LinearLayoutManager(this)
        recycler_package.adapter = mAdapter
        package_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mFilterList.clear()
                if (s.isNullOrEmpty()) {
                    mFilterList.addAll(mAppList)
                } else {
                    val list = mAppList.filter { it.appName.contains(s.toString(), true) }
                    mFilterList.addAll(list)
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
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
            mFilterList.clear()
            mFilterList.addAll(list)
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