package com.konstant.tool.ui.activity.toolactivity.im.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.R
import com.konstant.tool.base.BaseFragment
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem

/**
 * 描述:联系人页面
 * 创建人:菜籽
 * 创建时间:2018/1/10 下午4:49
 * 备注:
 */

class ContactsFragment:BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conversion_contact,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){

        SwipeMenuCreator{swipeLeftMenu, swipeRightMenu, viewType ->
            val rightMenu = SwipeMenuItem(mActivity)
            rightMenu.setBackgroundColor(Color.RED)
            rightMenu.text = "删除"
            swipeRightMenu.addMenuItem(rightMenu)
        }
    }

}