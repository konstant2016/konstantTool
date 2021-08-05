package com.konstant.tool.lite.module.diary

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantDialog
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import kotlinx.android.synthetic.main.activity_dog_diary.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：8/5/21 11:50 PM
 * 作者：吕卡
 * 备注：舔狗日记
 */

class DogDiaryActivity : BaseActivity() {

    private val mPresenter = DogDiaryPresenter(mDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_diary)
        setTitle(getString(R.string.dog_title))
        initView()
    }

    private fun initView() {
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { showDialog() }
        tv_content.setTextIsSelectable(true)
        tv_content.setOnLongClickListener {
            try {
                val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Label", tv_content.text)
                manager.setPrimaryClip(clipData)
                showToast(getString(R.string.dog_copy_success))
            } catch (e: Exception) {
                showToast(getString(R.string.dog_copy_error))
            }
            return@setOnLongClickListener true
        }
        refresh_layout.apply {
            setHeaderView(BezierLayout(this@DogDiaryActivity))
            setEnableLoadmore(false)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                    getDogDiary()
                }
            })
        }
        refresh_layout.startRefresh()
    }

    private fun getDogDiary() {
        mPresenter.getDogDiaryContent {
            if (!TextUtils.isEmpty(it)) {
                tv_content.text = it
            }
            refresh_layout.finishRefreshing()
        }
    }

    private fun showDialog() {
        KonstantDialog(this)
                .setTitle(getString(R.string.base_tips))
                .setMessage(getString(R.string.dog_dialog_content))
                .setPositiveListener { it.dismiss() }
                .createDialog()
    }
}