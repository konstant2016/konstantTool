package com.konstant.tool.lite.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_h5.*
import kotlinx.android.synthetic.main.pop_h5.view.*
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
    }

    private lateinit var mPop: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("加载中...")
        setContentView(R.layout.activity_h5)
        initBaseViews()
        val url = intent.getStringExtra(H5_URL)
        Log.d(TAG,url)
        view_web.loadUrl(url)
    }

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMorePressed() }
        }
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

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
        }
    }


    private fun onMorePressed() {
        val view = layoutInflater.inflate(R.layout.pop_h5, null)
        view.tv_refresh.setOnClickListener { mPop.dismiss();onRefresh() }
        view.tv_browser.setOnClickListener { mPop.dismiss();withBrowser() }
        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.showAsDropDown(title_bar)
    }

    private fun onRefresh() {
        view_web.reload()
    }

    private fun withBrowser() {
        val uri = Uri.parse(view_web.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (view_web.canGoBack()) {
            view_web.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        (view_web.parent as ViewGroup).removeView(view_web)
        view_web.apply {
            settings.javaScriptEnabled = false
            clearHistory()
            clearView()
            removeAllViews()
            destroy()
        }
        super.onDestroy()
    }
}
