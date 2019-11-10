package com.konstant.tool.lite.module.parse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.ClipboardManager
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.H5Activity
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_h5.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/8/2 15:30
 * 创建：菜籽
 * 描述：网络视频展示页面
 */

class NetVideoActivity : BaseActivity() {

    private val mUrls by lazy { resources.getStringArray(R.array.list_parse_url) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        setTitle(getString(R.string.base_loading))
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
        KonstantPopupWindow(this)
                .setItemList(listOf(getString(R.string.parse_refresh_current_page), getString(R.string.parse_copy_current_page), getString(R.string.parse_engine_01), getString(R.string.parse_engine_02), getString(R.string.parse_engine_03), getString(R.string.parse_engine_04), getString(R.string.parse_engine_05)))
                .setOnItemClickListener {
                    when (it) {
                        0 -> {
                            web_view.reload()
                        }
                        1 -> {
                            val manger = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            manger.text = web_view.url
                            showToast(getString(R.string.parse_save_clipboard_toast))
                        }
                        2, 3, 4, 5, 6 -> {
                            startHtmlActivity(mUrls[it - 2])
                        }
                    }
                }
                .showAsDropDown(title_bar)
    }

    private fun startHtmlActivity(url: String) {
        val param = "$url${web_view.url}"
        with(Intent(this, H5Activity::class.java)) {
            putExtra(H5Activity.H5_URL, param)
            putExtra(H5Activity.H5_BROWSER, true)
            startActivity(this)
        }
    }

    override fun onBackPressed() {
        if (web_view.onBackPressed()) return
        super.onBackPressed()
    }
}
