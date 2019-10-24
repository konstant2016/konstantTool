package com.konstant.tool.lite.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

open class BaseFragment : androidx.fragment.app.Fragment() {

    protected lateinit var mActivity: Activity
    protected val mDisposable = CompositeDisposable()

    private var isViewCreated = false
    protected var mIsVisibleToUser = false
    private var isFirstVisible = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        // onViewCreated只会调用一次，当调用此方法时，判断是否对用户可见，如果可见，调用懒加载方法
        if (mIsVisibleToUser) {
            onLazyLoad()
            isFirstVisible = false
            onFragmentResume()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        if (!isViewCreated) return  // 如果视图未创建完成，返回
        if (isVisibleToUser) {      // 如果对用户可见
            onFragmentResume()
            if (isFirstVisible) {   // 如果是第一次对用户可见，则调用懒加载
                onLazyLoad()
                isFirstVisible = false
            }
        } else {
            onFragmentPause()
        }
    }

    protected open fun onLazyLoad() {

    }

    protected open fun onFragmentResume() {

    }

    protected open fun onFragmentPause() {

    }

    override fun onAttach(context: Context) {
        val c = context
        mActivity = c as BaseActivity
        super.onAttach(context)
    }

    protected open fun isFragmentResume(): Boolean = isViewCreated and mIsVisibleToUser

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
