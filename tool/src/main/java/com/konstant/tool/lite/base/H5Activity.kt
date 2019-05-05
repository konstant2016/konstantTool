package com.konstant.tool.lite.base

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.activity_h5.*

/**
 * 时间：2019/5/5 12:10
 * 创建：吕卡
 * 描述：H5页面
 */

class H5Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        initBaseViews()
        val url = intent.getStringExtra("url")
        view_web.loadUrl(url)
    }

    override fun initBaseViews() {
        super.initBaseViews()
        view_web.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress == 100) {
                        view_progress.visibility = View.GONE
                    } else {
                        view_progress.visibility = View.VISIBLE
                        view_progress.progress = newProgress
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    setTitle("" + title)
                }
            }

            webViewClient = object :WebViewClient(){
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (view_web.canGoBack()) {
            view_web.goBack()
            return
        }
        super.onBackPressed()
    }
}
