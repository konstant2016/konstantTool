package com.konstant.tool.lite.base

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * 描述:所有fragment的父类，用于封装一下常用方法
 * 创建人:菜籽
 * 创建时间:2018/1/10 上午11:08
 * 备注:
 */

open class BaseFragment : Fragment() {

    protected val mDisposable = CompositeDisposable()

    private var isFirstVisible = true

    override fun onResume() {
        super.onResume()
        if (isFirstVisible) {
            onLazyLoad()
            isFirstVisible = false
            return
        }
    }

    protected open fun onLazyLoad() {

    }

    protected open fun setTitle(title: String) {
        if (activity == null) return
        if (activity !is BaseActivity) return
        (activity as BaseActivity).setTitle(title)
    }

    protected fun setSubTitle(title: String) {
        if (activity == null) return
        if (activity !is BaseActivity) return
        (activity as BaseActivity).setSubTitle(title)
    }

    // 隐藏软键盘
    protected fun hideSoftKeyboard() {
        if (activity!!.window?.attributes?.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(activity!!.currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }

    protected fun showToast(msg: String) {
        activity?.runOnUiThread { Toast.makeText(getNotNullContext(), msg, Toast.LENGTH_SHORT).show() }
    }

    fun getNotNullContext(): Context {
        return context ?: KonApplication.context
    }

    override fun onDestroy() {
        mDisposable.dispose()
        super.onDestroy()
    }
}
