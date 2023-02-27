package com.konstant.develop.debug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstant.develop.R
import kotlinx.android.synthetic.main.fragment_page_start_debug.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 时间：2023/2/23 10:43
 * 作者：吕卡
 * 备注：封装的老的Fragment的使用方式，每次fragment可见时会回调onPageStart()
 *      支持ViewPager(需要使用BEHAVIOR_SET_USER_VISIBLE_HINT)和hide两种形式
 */

class PageStartFragment(val color: Int) : Fragment() {

    private val resumed = AtomicBoolean(false)
    private val selected = AtomicBoolean(true)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page_start_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_bg.setBackgroundColor(color)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        selected.set(isVisibleToUser)
        if (!resumed.get()) return
        if (isVisibleToUser) {
            onPageStart()
        } else {
            onPageEnd()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        selected.set(!hidden)
        if (!resumed.get()) return
        if (hidden) {
            onPageEnd()
        } else {
            onPageStart()
        }
    }

    override fun onPause() {
        super.onPause()
        resumed.set(false)
        if (selected.get()) {
            onPageEnd()
        }
    }

    override fun onResume() {
        super.onResume()
        resumed.set(true)
        if (selected.get()) {
            onPageStart()
        }
    }

    private fun onPageStart() {
        Log.d("PageStartFragment", "onPageStart")
    }

    private fun onPageEnd() {
        Log.d("PageStartFragment", "onPageEnd")
    }

}