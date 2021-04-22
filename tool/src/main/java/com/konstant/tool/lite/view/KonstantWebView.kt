package com.konstant.tool.lite.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import android.widget.RelativeLayout
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.setting.SettingManager
import kotlinx.android.synthetic.main.layout_konstant_webview.view.*

class KonstantWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    interface WebViewStatusChangeListener {
        fun onShowLandscape() {}
        fun onShowPortrait() {}
        fun onTitleChanged(title: String) {}
    }

    private val mInterceptUrlList = listOf(
            "youku",
            "tenvideo",
            "sohuvideo",
            "iqiyi",
            "pptv",
            "letvclient",
            "tbopen",
            "openapp",
            "kuaidi100",
            "http://a.app.qq.com/",
            "hap://app/com",
            "https://mc.usihnbcq.cn",
            "https://1137.uosbp.com",
            "https://h5.bt1.club",
            "https://web.26knr.xyz",
            "https://h5.lyou.club",
            "https://9mrfwgxd41.mbrxt.com/",
            "https://okl.unionspice.com")

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_konstant_webview, this, true)
        initWebVew()
    }

    private var mListener: WebViewStatusChangeListener? = null

    private fun initWebVew() {
        web_view_inner.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            loadWithOverviewMode = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            displayZoomControls = false
            domStorageEnabled = true
        }

        web_view_inner.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                Log.d("KonstantWebView", url)
                mInterceptUrlList.forEach {
                    if (url.startsWith(it)) {
                        Toast.makeText(KonApplication.context, "客户端跳转已拦截，如仍要跳转，请复制链接后在浏览器中打开", Toast.LENGTH_LONG).show()
                        return true
                    }
                }
                view?.loadUrl(url)
                return false
            }
        }

        web_view_inner.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                mListener?.onTitleChanged(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    view_progress.visibility = View.GONE
                } else {
                    view_progress.visibility = View.VISIBLE
                    view_progress.progress = newProgress
                }
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                web_view_inner.visibility = View.GONE
                layout_placeholder.visibility = View.VISIBLE
                layout_placeholder.addView(view)
                mListener?.onShowLandscape()
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                layout_placeholder.removeAllViews()
                layout_placeholder.visibility = View.GONE
                web_view_inner.visibility = View.VISIBLE
                mListener?.onShowPortrait()
            }
        }
    }

    fun openOnBrowser(url: String = "") {
        val uri = if (url.isEmpty()) Uri.parse(getWebVew().url) else Uri.parse(url)
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

    fun onBackPressed(): Boolean {
        if (getWebVew().canGoBack()) {
            getWebVew().goBack()
            return true
        }
        return false
    }

    fun getWebVew() = web_view_inner

    fun setStatusChangeListener(listener: WebViewStatusChangeListener) {
        mListener = listener
    }

    fun loadUrl(url: String) {
        getWebVew().loadUrl(url)
    }

    fun reload() {
        getWebVew().reload()
    }

    fun getUrl(): String {
        return getWebVew().url
    }

    fun onDestroy() {
        getWebVew().apply {
            loadDataWithBaseURL("", "", "text/html", "utf-8", "")
            clearHistory()
            clearCache(true)
            removeAllViews()
            destroy()
        }
    }
}