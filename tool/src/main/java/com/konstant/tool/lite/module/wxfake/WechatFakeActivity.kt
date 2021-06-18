package com.konstant.tool.lite.module.wxfake

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.ImageSelector
import com.konstant.tool.lite.view.KonstantDialog
import com.konstant.tool.lite.view.KonstantPopupWindow
import com.konstant.tool.lite.view.StatusBarUtil
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

    private val mConversionList = arrayListOf<Conversion>()
    private val mAdapter by lazy { AdapterFake(mConversionList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wechat_fake)
        StatusBarUtil.setFullScreenBlack(this)
        showTitleBar(false)
        initViews()
    }

    // 微信对话界面 目前没有暗色主题，因此无需修改
    override fun setTheme(resid: Int) {
        super.setTheme(R.style.tool_lite_class)
    }

    private fun initViews() {
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight())
        layout_status_bar.layoutParams = params
        tv_adverse.setOnClickListener { finish() }
        img_more_fake.setOnClickListener {
            KonstantPopupWindow(this)
                    .setItemList(listOf(getString(R.string.wxfake_other_name), getString(R.string.wxfake_other_header), getString(R.string.wxfake_my_header), getString(R.string.wxfake_show_input)))
                    .setOnItemClickListener {
                        when (it) {
                            0 -> setAdverseName()
                            1 -> setAdverseHeader()
                            2 -> setMyHeader()
                            3 -> switchState()
                        }
                    }
                    .showAsDropDown(view_divider)
        }

        btn_adverse.setOnClickListener {
            showInputDialog(getString(R.string.wxfake_input_other_txt), ConversionType.TYPE_ADVERSE, ADVERSE_HEADER_NAME)
        }

        btn_time.setOnClickListener {
            showInputDialog(getString(R.string.wxfake_input_time_txt), ConversionType.TYPE_TIME)
        }

        btn_mine.setOnClickListener {
            showInputDialog(getString(R.string.wxfake_input_mine_txt), ConversionType.TYPE_MINE, MINE_HEADER_NAME)
        }

        with(layout_recycler) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@WechatFakeActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        img_back_fake.setOnClickListener { finish() }

        mAdapter.setOnItemLongClickListener { _, position ->
            showRemoveDialog(position)
        }
    }

    // 设置对方名字
    private fun setAdverseName() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        KonstantDialog(this)
                .addView(view)
                .setMessage(getString(R.string.wxfake_set_other_name))
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
            val msg = "${getString(R.string.wxfake_other_header)}${if (it) getString(R.string.base_txt_success) else getString(R.string.base_txt_fail)}"
            showToast(msg)
            if (it) mAdapter.notifyDataSetChanged()
        }
    }

    // 设置我的头像
    private fun setMyHeader() {
        ImageSelector.selectImg(this, MINE_HEADER_NAME) {
            val msg = "${getString(R.string.wxfake_my_header)}${if (it) getString(R.string.base_txt_success) else getString(R.string.base_txt_fail)}"
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

    private fun showInputDialog(msg: String, conversionType: Int, fileName: String = "") {
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        KonstantDialog(this)
                .addView(view)
                .setMessage(msg)
                .setPositiveListener {
                    if (view.edit_input.text.isNullOrEmpty()) return@setPositiveListener
                    mConversionList.add(Conversion(view.edit_input.text.toString(), conversionType, fileName))
                    mAdapter.notifyDataSetChanged()
                    it.dismiss()
                }
                .createDialog()
        showKeyboard(view.edit_input)
    }

    private fun showRemoveDialog(position: Int) {
        KonstantDialog(this)
                .setMessage(getString(R.string.wxfake_dialog_delete_conversion))
                .setPositiveListener {
                    mConversionList.removeAt(position)
                    mAdapter.notifyItemRemoved(position)
                }
                .createDialog()
    }
}
