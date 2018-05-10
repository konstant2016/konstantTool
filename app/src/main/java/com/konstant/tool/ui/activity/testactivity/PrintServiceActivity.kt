package com.konstant.tool.ui.activity.testactivity

import android.app.ActivityManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import kotlinx.android.synthetic.main.activity_print_service.*
import java.util.*

class PrintServiceActivity : BaseActivity() {

    private val colors = arrayOf(Color.BLACK, Color.BLUE,
            Color.parseColor("#FF00FF"),
            Color.parseColor("#9932CC"),
            Color.parseColor("#4682B4"),
            Color.parseColor("#00FA9A"),
            Color.parseColor("#0000EE"),
            Color.parseColor("#CD2626"),
            Color.RED, Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_service)
        setTitle("所有正在运行的服务")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        readAllRunningService()
        layout_refresh.setOnRefreshListener { readAllRunningService() }

        // 当ScrollView滑动时，禁止layout_refresh响应滑动事件
        layout_scroll.viewTreeObserver.addOnScrollChangedListener {
            layout_refresh.isEnabled = (layout_scroll.scrollY == 0)
        }
    }

    private fun readAllRunningService() {
        tv_service.text = ""
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = manager.getRunningServices(1000)
        list.sortBy { it.service.className.toString() }
        list.forEach { tv_service.append(it.service.className.toString());tv_service.append("\n\n") }
        val random = Random().nextInt(7)
        Log.i("颜色", "$random")
        tv_service.setTextColor(colors[random])
        layout_refresh.isRefreshing = false
    }
}
