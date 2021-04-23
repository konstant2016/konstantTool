package com.konstant.tool.lite.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.view.KonstantPopupWindow
import com.konstant.tool.lite.view.KonstantWebView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_h5.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/5/5 12:10
 * 创建：吕卡
 * 描述：H5页面
 */

open class H5Activity : BaseActivity() {

    companion object {
        private const val H5_URL = "url"
        private const val H5_BROWSER = "browser"  // 是否用浏览器打开

        fun openWebView(context: Context, url: String, openBrowser: Boolean = false) {
            with(Intent(context, H5Activity::class.java)) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(H5_URL, url)
                putExtra(H5_BROWSER, openBrowser)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        setTitle(getString(R.string.base_loading))
        val url = intent.getStringExtra(H5_URL) ?: ""
        val browser = intent.getBooleanExtra(H5_BROWSER, false)
        if (browser) {
            web_view.openOnBrowser(url)
            finish()
            return
        }
        initViews()
        loadUrl(url)
    }

    fun loadUrl(url: String) {
        web_view.loadUrl(url)
    }

    private fun initViews() {
        window.setFormat(PixelFormat.TRANSLUCENT)
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMorePressed() }
        }
        web_view.setStatusChangeListener(object : KonstantWebView.WebViewStatusChangeListener {
            override fun onTitleChanged(title: String) {
                setTitle(title)
            }

            override fun onShowLandscape() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                showTitleBar(false)
            }

            override fun onShowPortrait() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                showTitleBar(true)
            }
        })
        img_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun recreateOnConfigChanged(): Boolean {
        return false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
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

    override fun onBackPressed() {
        if (web_view.onBackPressed()) return
        super.onBackPressed()
    }

    override fun onDestroy() {
        web_view.onDestroy()
        super.onDestroy()
    }
}
