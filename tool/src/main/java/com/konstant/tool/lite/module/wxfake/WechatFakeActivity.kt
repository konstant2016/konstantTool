package com.konstant.tool.lite.module.wxfake

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.pop_wechat.view.*

/**
 * 时间：2019/4/22 14:39
 * 创建：吕卡
 * 描述：模拟微信对话
 */

class WechatFakeActivity : BaseActivity() {

    lateinit var mPop: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideTitleBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{
            window.statusBarColor = Color.BLACK
        }
        setContentView(R.layout.activity_wechat_fake)
    }

    override fun initBaseViews() {
        with(LayoutInflater.from(this).inflate(R.layout.pop_wechat, null)){
            tv_set_adverse_name.setOnClickListener { setAdverseName() }
            tv_set_adverse_header.setOnClickListener { setAdverseHeader() }
            tv_set_mine_header.setOnClickListener { setMyHeader() }
            mPop = PopupWindow(this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
            mPop.showAsDropDown(title_bar)
        }
    }

    // 设置对方名字
    private fun setAdverseName(){

    }

    // 设置对方头像
    private fun setAdverseHeader(){

    }

    // 设置我的头像
    private fun setMyHeader(){

    }


}
