package com.konstant.tool.lite.view

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import com.konstant.tool.lite.R

class KonstantViewDialog(private val context: AppCompatActivity) : PopupWindow() {

    private var mMessage: TextView
    private lateinit var mPop: PopupWindow
    private lateinit var mLayout:RelativeLayout

    private lateinit var listener: (KonstantViewDialog) -> Unit

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_konstant_dialog_view, null)
        mMessage = view.findViewById(R.id.tv_message)
        mLayout = view.findViewById(R.id.layout_view)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            listener(this)
        }
        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener { mPop.dismiss() }
        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.animationStyle = R.style.popwin_anim_style
        mPop.setOnDismissListener { backgroundAlpha(1.0f) }
        mPop.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

    }

    fun setMessage(text: String): KonstantViewDialog {
        mMessage.text = text
        return this
    }

    fun show() {
        val parent = (context.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        backgroundAlpha(0.85f)
        mPop.showAtLocation(parent, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    override fun dismiss(){
        mPop.dismiss()
    }

    fun setPositiveListener(onPositive: (dialog:KonstantViewDialog) -> Unit): KonstantViewDialog {
        listener = onPositive
        return this
    }

    fun addView(view:View):KonstantViewDialog{
        mLayout.addView(view,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
        return this
    }

    private fun backgroundAlpha(bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        context.window.attributes = lp
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

}