package com.konstant.tool.ui.activity.testactivity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.konstant.tool.R
import kotlinx.android.synthetic.main.activity_coording.*
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

class CoordinatorActivity : SwipeBackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coording)
        title = "可折叠状态栏"
        if (Build.VERSION.SDK_INT >= 21) {
            val option =
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or             // 如果不设置，则布局不会延伸到导航栏
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or          // 如果不设置，则布局不会延伸到状态栏
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or              // 保持整合view稳定，通常和SYSTEM_UI_FLAG_LIGHT_STATUS_BAR共用
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR              // 当需要状态栏字体图标展示为黑色时，调用此方法
            window.decorView.systemUiVisibility = option
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        }

//        setSupportActionBar(tool_bar)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_input_add)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.d("移动高度",""+verticalOffset)
            if (Math.abs(verticalOffset) >= 200) {
            }else{
            }
            if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {

            }
        }

    }
}

