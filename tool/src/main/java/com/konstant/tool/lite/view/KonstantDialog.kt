package com.konstant.tool.lite.view

import android.app.Dialog
import android.content.Context
import android.view.*
import com.konstant.tool.lite.R
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
    private var messageGravity: Int = Gravity.CENTER
    private var title: String = ""
    private var cancelText: String = context.getString(R.string.base_cancel)
    private var confirmText: String = context.getString(R.string.base_confirm)
    private var view: View? = null           // 内部填充的布局
    private val mList = ArrayList<String>()  // 内部填充的列表

    private var positiveListener: ((KonstantDialog) -> Unit)? = null     // 确认按钮按下后
    private var negativeListener: ((KonstantDialog) -> Unit)? = null     // 取消按钮按下后
    private var checkedChangeListener: ((state: Boolean) -> Unit)? = null// checkbox状态监听
    private var mOnItemClickListener: ((dialog: KonstantDialog, position: Int) -> Unit)? = null   // 列表的点击回调

    private lateinit var root: View

    // 设置标题
    fun setTitle(msg: String): KonstantDialog {
        title = msg
        return this
    }

    // 设置信息
    fun setMessage(msg: String, gravity: Int = Gravity.CENTER): KonstantDialog {
        message = msg
        messageGravity = gravity
        return this
    }

    // 设置内部控件
    fun addView(view: View): KonstantDialog {
        this.view = view
        return this
    }

    // 设置确认按钮的监听
    fun setPositiveListener(text: String = context.getString(R.string.base_confirm), listener: (KonstantDialog) -> Unit): KonstantDialog {
        confirmText = text
        positiveListener = listener
        return this
    }

    // 设置取消按钮的监听
    fun setNegativeListener(text: String = context.getString(R.string.base_cancel), listener: (KonstantDialog) -> Unit): KonstantDialog {
        cancelText = text
        negativeListener = listener
        return this
    }

    // 设置列表点击监听
    fun setOnItemClickListener(listener: (dialog: KonstantDialog, position: Int) -> Unit): KonstantDialog {
        mOnItemClickListener = listener
        return this
    }

    // 设置填充列表
    fun setItemList(stringList: List<String>): KonstantDialog {
        mList.clear()
        mList.addAll(stringList)
        return this
    }

    // 显示、隐藏 checkbox
    fun setCheckedChangeListener(listener: ((state: Boolean) -> Unit)): KonstantDialog {
        checkedChangeListener = listener
        return this
    }

    // 是否可以点击外部取消
    fun setOutsideCancelable(cancelable: Boolean): KonstantDialog {
        setCancelable(cancelable)
        return this
    }

    // 根据builder创建dialog
    fun createDialog(): KonstantDialog {
        root = LayoutInflater.from(context).inflate(R.layout.layout_dialog_konstant, null)
        if (hideNavigation) {
            root.layout_navigation.visibility = View.GONE
        }

        root.btn_confirm.text = confirmText
        root.btn_confirm.setOnClickListener {
            positiveListener?.invoke(this)
        }

        root.btn_cancel.text = cancelText
        root.btn_cancel.setOnClickListener {
            negativeListener?.invoke(this)
            this.dismiss()
        }

        if (message.isNotEmpty()) {
            root.tv_message.text = message
            root.tv_message.gravity = messageGravity
        } else {
            root.tv_message.visibility = View.GONE
        }

        if (title.isNotEmpty()) {
            root.tv_title.text = title
        } else {
            root.tv_title.visibility = View.GONE
        }

        if (mList.isNotEmpty()) {
            root.view_list.apply {
                visibility = View.VISIBLE
                adapter = Adapter(context, mList)
                setOnItemClickListener { _, _, position, _ ->
                    mOnItemClickListener?.invoke(this@KonstantDialog, position)
                }
            }
        }

        if (checkedChangeListener != null) {
            root.layout_checkbox.visibility = View.VISIBLE
            root.checkbox.setOnCheckedChangeListener { _, isChecked ->
                checkedChangeListener?.invoke(isChecked)
            }
        }

        if (view != null) {
            root.layout_content.apply {
                visibility = View.VISIBLE
                removeAllViews()
                addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }
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
        window?.apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
            decorView.setPadding(0, 0, 0, 0)
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            setWindowAnimations(R.style.popwin_anim_style)
            super.show()
        }
    }

    @Deprecated("", ReplaceWith("createDialog()", "android.app.Dialog"))
    override fun show() {
        super.show()
    }

    override fun dismiss() {
        root.layout_content.removeAllViews()
        super.dismiss()
    }

}