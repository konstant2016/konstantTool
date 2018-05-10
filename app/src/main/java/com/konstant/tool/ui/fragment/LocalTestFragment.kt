package com.konstant.tool.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.R
import com.konstant.tool.base.BaseFragment
import com.konstant.tool.ui.activity.opengl.OpenGlActivity
import com.konstant.tool.ui.activity.testactivity.*
import kotlinx.android.synthetic.main.fragment_local_test.*

/**
 * 描述:本地测试一些代码的工具
 * 创建人:菜籽
 * 创建时间:2017/12/28 下午5:46
 * 备注:
 */

class LocalTestFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local_test, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 第一种时钟
        firstClock.setOnClickListener { startActivity(Intent(mActivity, FirstClockActivity::class.java)) }

        // 第二种时钟
        secondClock.setOnClickListener { startActivity(Intent(mActivity, SecondClockActivity::class.java)) }

        // VIP解析
        break_vip.setOnClickListener { startActivity(Intent(mActivity, VideoCrackActivity::class.java)) }

        // 圆环进度条
        progress_circle.setOnClickListener { startActivity(Intent(mActivity, CircleActivity::class.java)) }

        // 圆环刻度进度
        progress_scale_circle.setOnClickListener { startActivity(Intent(mActivity, ScaleCircleActivity::class.java)) }

        // 手势缩放字体
        gesture_zoom.setOnClickListener { startActivity(Intent(mActivity, GestureActivity::class.java)) }

        // 可折叠状态栏
        full_screen_status_test.setOnClickListener { startActivity(Intent(mActivity, CoordinatorActivity::class.java)) }

        // 侧滑菜单
        drawer_layout.setOnClickListener { startActivity(Intent(mActivity, DrawerlayoutActivity::class.java)) }

        // apk完整性校验
        apk_checkout.setOnClickListener { startActivity(Intent(mActivity, ICVActivity::class.java)) }

        // 打印所有服务
        layout_print_service.setOnClickListener { startActivity(Intent(mActivity, PrintServiceActivity::class.java)) }

        // OpenGL实战
        layout_open_gl.setOnClickListener { startActivity(Intent(mActivity, OpenGlActivity::class.java)) }

        // 自定义的触摸控件
        layout_touch_view.setOnClickListener { startActivity(Intent(mActivity, TouchActivity::class.java)) }
    }
}