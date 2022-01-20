package com.konstant.develop.x5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import com.konstant.develop.R
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_tencent_x5.*

/**
 * 离线加载腾讯的X5内核
 */

class TencentX5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tencent_x5)

        configWebView()
//        x5_web_view.loadUrl("https://vs-video.yangcong345.com/pingbanfahuo-e43cd8bcd8b7d016a264e2fe8adb5e6d.mp4")
//        x5_web_view.loadUrl("http://debugtbs.qq.com")
        x5_web.loadUrl("https://test.yangcong345.com/school/activityH5/longhua/signup")
    }

    private fun configWebView(){
        x5_web.webChromeClient = WebChromeClient()
        x5_web.webViewClient = WebViewClient()
        x5_web.settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            allowFileAccess = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
    }
}