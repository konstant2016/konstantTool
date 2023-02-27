package com.konstant.develop.x5

import android.os.Bundle
import android.util.Log
import android.webkit.*
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_system_web.*

class SystemWebActivity : BaseActivity() {

    val interceptUrl = "https://b.bdstatic.com/searchbox/file/cmsuploader/20221015/1665804808250258.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_web)
        configWebView()
        system_web.loadUrl("https://www.baidu.com/")
    }

    private fun configWebView() {
        system_web.webChromeClient = WebChromeClient()
        system_web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                Log.d("configWebView",request?.requestHeaders?.get("Referer")?:"")
                request?.requestHeaders?.get("referer")
                try {
//                    return WebResourceResponse("","",null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return super.shouldInterceptRequest(view, request)
            }
        }

        system_web.settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            allowFileAccess = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
    }

}