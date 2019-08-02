package com.konstant.tool.lite.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class KonstantWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {

    private var mTitleChange: ((String) -> Unit)? = null
    private var mProgressChange: ((Int) -> Unit)? = null

    init {
        settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            loadWithOverviewMode = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            displayZoomControls = false
        }


        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                mTitleChange?.invoke(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mProgressChange?.invoke(newProgress)
            }
        }
    }

    fun registerTitleChanged(titleChange: (String) -> Unit) {
        mTitleChange = titleChange
    }

    fun registerProgressChanged(progressChange: (Int) -> Unit) {
        mProgressChange = progressChange
    }

    fun onDestroy() {
        loadDataWithBaseURL("", "", "text/html", "utf-8", "")
        clearHistory()
        clearCache(true)
        removeAllViews()
        destroy()
    }

    fun onBackPressed(): Boolean {
        if (canGoBack()) {
            goBack()
            return true
        }
        return false
    }

}