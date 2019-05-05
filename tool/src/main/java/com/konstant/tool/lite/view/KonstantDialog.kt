package com.konstant.tool.lite.view

import android.app.Dialog
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.RelativeLayout
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.layout_dialog_konstant.*
import kotlinx.android.synthetic.main.layout_dialog_konstant.view.*

/**
 * 描述:自定义的dialog
 * 创建人:菜籽
 * 创建时间:2018/4/18 下午2:36
 * 备注:
 */

open class KonstantDialog(context: Context) : Dialog(context, R.style.KonstantDialog) {

    private var hideNavigation = false
    private var message: String = ""
    private var view: View? = null           // 内部填充的布局

    private var positiveListener: ((KonstantDialog) -> Unit)? = null     // 确认按钮按下后
    private var negativeListener: ((KonstantDialog) -> Unit)? = null     // 取消按钮按下后
    private var checkedChangeListener: ((state: Boolean) -> Unit)? = null// checkbox状态监听

    private lateinit var root: View

    // 设置信息
    fun setMessage(msg: String): KonstantDialog {
        message = msg
        return this
    }

    // 设置内部控件
    fun addView(view: View): KonstantDialog {
        this.view = view
        return this
    }

    // 设置确认按钮的监听
    fun setPositiveListener(listener: (KonstantDialog) -> Unit): KonstantDialog {
        positiveListener = listener
        return this
    }

    // 设置取消按钮的监听
    fun setNegativeListener(listener: (KonstantDialog) -> Unit): KonstantDialog {
        negativeListener = listener
        return this
    }

    // 显示、隐藏 checkboc
    fun setCheckedChangeListener(listener: ((state: Boolean) -> Unit)): KonstantDialog {
        checkedChangeListener = listener
        return this
    }

    // 根据builder创建dialog
    fun createDialog(): KonstantDialog {
        root = LayoutInflater.from(context).inflate(R.layout.layout_dialog_konstant, null)
        if (hideNavigation) {
            root.layout_navigation.visibility = View.GONE
        }

        root.btn_confirm.setOnClickListener {
            positiveListener?.invoke(this)
        }

        root.btn_cancel.setOnClickListener {
            negativeListener?.invoke(this)
            this.dismiss()
        }

        if (message.isNotEmpty()) {
            root.tv_message.text = message
        } else {
            root.tv_message.visibility = View.GONE
        }

        if (checkedChangeListener != null) {
            root.layout_checkbox.visibility = View.VISIBLE
            root.checkbox.setOnCheckedChangeListener { _, isChecked ->
                checkedChangeListener!!.invoke(isChecked)
            }
        }

        if (view != null) {
            root.layout_view.removeAllViews()
            root.layout_view.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        addContentView(root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        showDialog()
        return this
    }

    // 隐藏确认和取消按钮
    fun hideNavigation(): KonstantDialog {
        hideNavigation = true
        return this
    }

    private fun showDialog() {
        window.apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
            decorView.setPadding(0, 0, 0, 0)
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            setWindowAnimations(R.style.popwin_anim_style)
            show()
        }
    }

    override fun dismiss() {
        root.layout_view.removeAllViews()
        super.dismiss()
    }

}