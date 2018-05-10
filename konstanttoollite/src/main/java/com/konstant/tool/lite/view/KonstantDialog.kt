package com.konstant.tool.lite.view

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.konstant.tool.lite.R

/**
 * 描述:自定义的dialog
 * 创建人:菜籽
 * 创建时间:2018/4/18 下午2:36
 * 备注:
 */

class KonstantDialog(context: Context) : Dialog(context, R.style.KonstantDialog) {

    private var hideNavigation = false
    private var message: String = ""
    private var view: View? = null           // 内部填充的布局

    private var positiveListener: ((KonstantDialog) -> Unit)? = null     // 确认按钮按下后
    private var negativeListener: ((KonstantDialog) -> Unit)? = null     // 取消按钮按下后

    private lateinit var root: View
    private lateinit var child: RelativeLayout

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

    // 根据builder创建dialog
    fun createDialog() {
        root = LayoutInflater.from(context).inflate(R.layout.layout_dialog_konstant, null)
        if (hideNavigation){
            root.findViewById(R.id.layout_navigation).visibility = View.GONE
        }
        child = root.findViewById(R.id.layout_view) as RelativeLayout

        root.findViewById(R.id.btn_confirm).setOnClickListener {
            positiveListener?.invoke(this)
        }

        root.findViewById(R.id.btn_cancel).setOnClickListener {
            negativeListener?.invoke(this)
            this.dismiss()
        }

        if (message.isNotEmpty()) {
            (root.findViewById(R.id.tv_message) as TextView).text = message
        } else {
            root.findViewById(R.id.tv_message).visibility = View.GONE
        }

        if (view != null) {
            child.removeAllViews()
            child.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        addContentView(root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        showDialog()
    }

    // 隐藏确认和取消按钮
    fun hideNavigation():KonstantDialog{
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
        child.removeAllViews()
        super.dismiss()
    }

}