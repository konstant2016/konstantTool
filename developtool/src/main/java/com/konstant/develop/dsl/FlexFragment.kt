package com.konstant.develop.dsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstant.develop.R

/**
 * 时间：2022/2/15 6:21 下午
 * 作者：吕卡
 * 备注：弹性布局
 */

class FlexFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flex, container, false)
    }

}