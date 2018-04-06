package com.konstant.toollite.view

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import com.konstant.toollite.R


/**
 * 描述:确认对话框
 * 创建人:菜籽
 * 创建时间:2018/4/6 上午12:03
 * 备注:
 */

class KonstantConfirmtDialog(private val context: Activity) : PopupWindow(context) {

    private var mMessage: TextView
    private lateinit var mPop: PopupWindow

    private lateinit var positiveListener: (KonstantConfirmtDialog) -> Unit
    private lateinit var negativeListener: (KonstantConfirmtDialog) -> Unit

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_konstant_dialog, null)
        mMessage = view.findViewById(R.id.tv_message)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            positiveListener(this)
        }
        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            mPop.dismiss()
            negativeListener(this)
        }
        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.animationStyle = R.style.popwin_anim_style
        mPop.setOnDismissListener { backgroundAlpha(1.0f) }

    }

    fun setMessage(text: String): KonstantConfirmtDialog {
        mMessage.text = text
        return this
    }

    fun show() {
        val parent = (context.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        backgroundAlpha(0.85f)
        mPop.showAtLocation(parent, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    override fun dismiss() {
        mPop.dismiss()
    }

    fun setPositiveListener(onPositive: (KonstantConfirmtDialog) -> Unit): KonstantConfirmtDialog {
        positiveListener = onPositive
        return this
    }

    fun setNegativeListener(negative: (KonstantConfirmtDialog) -> Unit): KonstantConfirmtDialog {
        negativeListener = negative
        return this
    }

    private fun backgroundAlpha(bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        context.window.attributes = lp
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

}
