package com.konstant.tool.lite.module.parse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.H5Activity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_net.*
import kotlinx.android.synthetic.main.pop_net_video.view.*
import kotlinx.android.synthetic.main.title_layout.*

/**
* 时间：2019/8/2 15:30
* 创建：菜籽
* 描述：网络视频展示页面
*/

class NetVideoActivity : BaseActivity() {

    private val mUrls by lazy { resources.getStringArray(R.array.list_parse_url) }
    lateinit var mPop: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        setTitle("加载中...")
        initBaseViews()
        intent?.getStringExtra("url")?.let { web_view.loadUrl(it) }
    }

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { onMoreClick() }
        web_view.registerTitleChanged(::setTitle)
        web_view.registerProgressChanged {
            if (it == 100) {
                view_progress.visibility = View.GONE
            } else {
                view_progress.visibility = View.VISIBLE
                view_progress.progress = it
            }
        }
    }

    private fun onMoreClick() {
        with(LayoutInflater.from(this).inflate(R.layout.pop_net_video, null)) {
            tv_refresh.setOnClickListener { web_view.reload();mPop.dismiss() }
            tv_copy_url.setOnClickListener {
                val manger = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                manger.text = web_view.url
                showToast("当前地址已保存至剪切板")
                mPop.dismiss()
            }
            tv_parse_01.setOnClickListener { startHtmlActivity(mUrls[0]) }
            tv_parse_02.setOnClickListener { startHtmlActivity(mUrls[1]) }
            tv_parse_03.setOnClickListener { startHtmlActivity(mUrls[2]) }
            tv_parse_04.setOnClickListener { startHtmlActivity(mUrls[3]) }
            tv_parse_05.setOnClickListener { startHtmlActivity(mUrls[4]) }
            mPop = PopupWindow(this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
            mPop.showAsDropDown(title_bar)
        }
    }

    private fun startHtmlActivity(url: String) {
        val param = "$url${web_view.url}"
        with(Intent(this, H5Activity::class.java)) {
            putExtra(H5Activity.H5_URL, param)
            putExtra(H5Activity.H5_BROWSER, true)
            startActivity(this)
        }
        mPop.dismiss()
    }

    override fun onBackPressed() {
        if (web_view.onBackPressed()) return
        super.onBackPressed()
    }
}
