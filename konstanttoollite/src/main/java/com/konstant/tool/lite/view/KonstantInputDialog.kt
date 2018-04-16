package com.konstant.tool.lite.view

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.konstant.tool.lite.R

/**
 * 描述:带有输入框的dialog
 * 创建人:菜籽
 * 创建时间:2018/4/6 上午1:26
 * 备注:
 */


class KonstantInputDialog(private val context: Activity) : PopupWindow() {

    private var mMessage: TextView
    private var mEdit:EditText
    private lateinit var mPop: PopupWindow

    private lateinit var listener: (String) -> Unit

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_konstant_dialog_input, null)
        mMessage = view.findViewById(R.id.tv_message)
        mEdit = view.findViewById(R.id.et_input)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            mPop.dismiss()
            if (!TextUtils.isEmpty(mEdit.text)){
                listener(mEdit.text.toString())
            }
        }
        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener { mPop.dismiss() }
        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.animationStyle = R.style.popwin_anim_style
        mPop.setOnDismissListener { backgroundAlpha(1.0f) }
        mPop.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }

    fun setMessage(text: String): KonstantInputDialog {
        mMessage.text = text
        return this
    }

    fun show() :KonstantInputDialog{
        val parent = (context.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        backgroundAlpha(0.85f)
        mPop.showAtLocation(parent, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
        showKeyboard(mEdit)
        return this
    }

    fun setPositiveListener(onPositive: (text:String) -> Unit): KonstantInputDialog {
        listener = onPositive
        return this
    }

    private fun backgroundAlpha(bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        context.window.attributes = lp
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun showKeyboard(editText:EditText) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, 0)
    }

}