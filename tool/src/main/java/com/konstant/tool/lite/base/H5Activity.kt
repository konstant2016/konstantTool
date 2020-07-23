package com.konstant.tool.lite.base

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_h5.*
import kotlinx.android.synthetic.main.title_layout.*


/**
 * 时间：2019/5/5 12:10
 * 创建：吕卡
 * 描述：H5页面
 */

class H5Activity : BaseActivity() {

    companion object {
        private val TAG = "H5Activity"
        val H5_URL = "url"
        val H5_BROWSER = "browser"  // 是否用浏览器打开
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        setTitle(getString(R.string.base_loading))
        val url = intent.getStringExtra(H5_URL)
        val browser = intent.getBooleanExtra(H5_BROWSER, false)
        if (browser) {
            web_view.openOnBrowser(url)
            finish()
            return
        }
        initViews()
        Log.d(TAG, url)
        web_view.loadUrl(url)
    }

    private fun initViews() {
        window.setFormat(PixelFormat.TRANSLUCENT)
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMorePressed() }
        }
        web_view.apply {
            registerTitleChanged(::setTitle)
            registerProgressChanged {
                if (it == 100) {
                    view_progress.visibility = View.GONE
                } else {
                    view_progress.visibility = View.VISIBLE
                    view_progress.progress = it
                }
            }
        }
    }

    private fun onMorePressed() {
        KonstantPopupWindow(this)
                .setItemList(listOf(getString(R.string.h5_activity_refresh_page), getString(R.string.h5_activity_open_with_browser)))
                .setOnItemClickListener {
                    when (it) {
                        0 -> {
                            onRefresh()
                        }
                        1 -> {
                            web_view.openOnBrowser()
                        }
                    }
                }
                .showAsDropDown(title_bar)
    }

    private fun onRefresh() {
        web_view.reload()
    }

    private fun withBrowser(url: String = web_view.url) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (web_view.onBackPressed()) return
        super.onBackPressed()
    }

    override fun onDestroy() {
        web_view.onDestroy()
        super.onDestroy()
    }
}
