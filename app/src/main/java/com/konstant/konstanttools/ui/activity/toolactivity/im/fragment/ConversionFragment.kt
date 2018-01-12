package com.konstant.konstanttools.ui.activity.toolactivity.im.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseFragment
import com.konstant.konstanttools.ui.activity.toolactivity.im.adapter.RecyclerAdapter
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_conversion_chat.*

/**
 * 描述:会话页面
 * 创建人:菜籽
 * 创建时间:2018/1/10 上午11:02
 * 备注:
 */

class ConversionFragment : BaseFragment() {

    private val mAdapter by lazy { RecyclerAdapter() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_conversion_chat, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        layout_recycler.layoutManager = LinearLayoutManager(mActivity)

        // 添加右侧侧滑菜单
        layout_recycler.setSwipeMenuCreator { _, swipeRightMenu, _ ->
            val rightMenu = SwipeMenuItem(mActivity)
            rightMenu.setBackgroundColor(Color.RED)
            rightMenu.text = "删除"
            rightMenu.height = WindowManager.LayoutParams.MATCH_PARENT
            rightMenu.width = 250
            rightMenu.setTextColor(Color.WHITE)
            swipeRightMenu.addMenuItem(rightMenu)
        }

        //  侧滑删除，长按拖拽
//        layout_recycler.isItemViewSwipeEnabled = true
//        layout_recycler.isLongPressDragEnabled = true

        // 右滑删除
        layout_recycler.setSwipeMenuItemClickListener { menuBridge ->
            menuBridge.closeMenu()
            mAdapter.size -= 1
            mAdapter.notifyItemRemoved(menuBridge.adapterPosition)
        }

        layout_recycler.adapter = mAdapter

    }

}