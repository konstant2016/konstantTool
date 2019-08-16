package com.konstant.tool.lite.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.setting.SettingManager

class KonstantWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.webViewStyle) : WebView(context, attrs, defStyleAttr) {

    private var mTitleChange: ((String) -> Unit)? = null
    private var mProgressChange: ((Int) -> Unit)? = null
    private var mTimeStamp = 0L

    private val mInterceptUrlList = listOf("youku", "tenvideo", "sohuvideo", "iqiyi", "pptv", "letvclient")

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
                url?.let {
                    mInterceptUrlList.forEach {
                        if (url.startsWith(it)) {
                            val millis = System.currentTimeMillis()
                            if (millis - mTimeStamp > 5000) {
                                Toast.makeText(KonApplication.context,"客户端跳转已拦截，如仍要跳转，请复制链接后在浏览器中打开",Toast.LENGTH_LONG).show()
                                mTimeStamp = millis
                            }
                            return false
                        }
                    }
                }
                view?.loadUrl(url)
                return true
            }
        }

        webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                mTitleChange?.invoke(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
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

    fun openOnBrowser(url: String = "") {
        val uri = if (url.isEmpty()) Uri.parse(getUrl()) else Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        when (SettingManager.getBrowserType(context)) {
            // Chrome Browser
            1 -> intent.setClassName("com.android.chrome", "org.chromium.chrome.browser.ChromeTabbedActivity")
            // QQ浏览器
            2 -> intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity")
            // UC浏览器
            3 -> intent.setClassName("com.UCMobile", "com.uc.browser.InnerUCMobile")
            // 系统浏览器
            4 -> intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "未发现指定浏览器，将以默认方式打开", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
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