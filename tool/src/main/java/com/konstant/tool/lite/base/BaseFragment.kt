package com.konstant.tool.lite.base

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.konstant.tool.lite.module.weather.param.SubTitleChanged
import com.konstant.tool.lite.module.weather.param.TitleChanged
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

/**
 * 描述:所有fragment的父类，用于封装一下常用方法
 * 创建人:菜籽
 * 创建时间:2018/1/10 上午11:08
 * 备注:
 */

open class BaseFragment : Fragment() {

    protected lateinit var mActivity: Activity
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

    override fun onAttach(context: Context) {
        val c = context
        mActivity = c as BaseActivity
        super.onAttach(context)
    }

    protected open fun setTitle(title: String) {
        EventBus.getDefault().post(TitleChanged(title))
    }

    protected fun setSubTitle(title: String) {
        EventBus.getDefault().post(SubTitleChanged(title))
    }

    // 隐藏软键盘
    protected fun hideSoftKeyboard() {
        if (mActivity.window?.attributes?.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        (mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(mActivity.currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }

    protected fun showToast(msg: String) {
        mActivity.runOnUiThread {
            Toast.makeText(mActivity.applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        mDisposable.dispose()
        super.onDestroy()
    }
}
