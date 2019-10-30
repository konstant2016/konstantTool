package com.konstant.tool.lite.module.wxfake

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.ImageSelector
import com.konstant.tool.lite.view.KonstantDialog
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_wechat_fake.*
import kotlinx.android.synthetic.main.layout_dialog_input.view.*

/**
 * 时间：2019/4/22 14:39
 * 创建：吕卡
 * 描述：模拟微信对话
 */

class WechatFakeActivity : BaseActivity() {

    private val ADVERSE_HEADER_NAME = "adverse_header"
    private val MINE_HEADER_NAME = "mine_header"

    private val list = arrayListOf<Conversion>()
    private val mAdapter by lazy { AdapterFake(list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wechat_fake)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.statusBarColor = Color.BLACK
        }
        showTitleBar(false)
        initBaseViews()
    }

    // 微信对话界面 目前没有暗色主题，因此无需修改
    override fun setTheme(resid: Int) {
        super.setTheme(R.style.tool_lite_class)
    }

    override fun initBaseViews() {
        tv_adverse.setOnClickListener { finish() }
        img_more_fake.setOnClickListener {
            KonstantPopupWindow(this)
                    .setItemList(listOf("设置对方昵称", "设置对方头像", "设置我的头像", "切换输入框"))
                    .setOnItemClickListener {
                        when (it) {
                            0 -> {
                                setAdverseName()
                            }
                            1 -> {
                                setAdverseHeader()
                            }
                            2 -> {
                                setMyHeader()
                            }
                            3 -> {
                                switchState()
                            }
                        }
                    }
                    .showAsDropDown(view_divider)
        }

        btn_adverse.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
            KonstantDialog(this)
                    .addView(view)
                    .setMessage("添加对方文字")
                    .setPositiveListener {
                        if (view.edit_input.text.isNullOrEmpty()) return@setPositiveListener
                        list.add(Conversion(view.edit_input.text.toString(), 0, ADVERSE_HEADER_NAME))
                        mAdapter.notifyDataSetChanged()
                        it.dismiss()
                    }
                    .createDialog()
            showKeyboard(view.edit_input)
        }

        btn_mine.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
            KonstantDialog(this)
                    .addView(view)
                    .setMessage("添加我方文字")
                    .setPositiveListener {
                        if (view.edit_input.text.isNullOrEmpty()) return@setPositiveListener
                        list.add(Conversion(view.edit_input.text.toString(), 1, MINE_HEADER_NAME))
                        mAdapter.notifyDataSetChanged()
                        it.dismiss()
                    }
                    .createDialog()
            showKeyboard(view.edit_input)
        }

        with(layout_recycler) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@WechatFakeActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        img_back_fake.setOnClickListener { finish() }

    }

    // 设置对方名字
    private fun setAdverseName() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        KonstantDialog(this)
                .addView(view)
                .setMessage("设置对方名字")
                .setPositiveListener {
                    if (view.edit_input.text.isNullOrEmpty()) return@setPositiveListener
                    tv_adverse.text = view.edit_input.text
                    it.dismiss()
                }
                .createDialog()
        showKeyboard(view.edit_input)
    }

    // 设置对方头像
    private fun setAdverseHeader() {
        ImageSelector.selectImg(this, ADVERSE_HEADER_NAME) {
            val msg = "对方头像设置${if (it) "成功" else "失败"}"
            showToast(msg)
            if (it) mAdapter.notifyDataSetChanged()
        }
    }

    // 设置我的头像
    private fun setMyHeader() {
        ImageSelector.selectImg(this, MINE_HEADER_NAME) {
            val msg = "我的头像设置${if (it) "成功" else "失败"}"
            showToast(msg)
            if (it) mAdapter.notifyDataSetChanged()
        }
    }

    // 隐藏，显示 输入按钮
    private fun switchState() {
        if (layout_build.visibility == View.VISIBLE) {
            layout_build.visibility = View.GONE
        } else {
            layout_build.visibility = View.VISIBLE
        }
    }
}
