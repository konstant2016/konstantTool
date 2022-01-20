package com.konstant.develop.x5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.konstant.develop.R
import kotlinx.android.synthetic.main.activity_system_web.*

class SystemWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_web)
        configWebView()
        system_web.loadUrl("https://test.yangcong345.com/school/activityH5/longhua/signup")
    }

    private fun configWebView() {
        system_web.webChromeClient = WebChromeClient()
        system_web.webViewClient = WebViewClient()
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