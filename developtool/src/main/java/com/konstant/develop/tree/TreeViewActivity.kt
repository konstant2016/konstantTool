package com.konstant.develop.tree

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.gson.Gson
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import com.otaliastudios.zoom.ZoomApi
import kotlinx.android.synthetic.main.activity_tree_view.*
import kotlinx.android.synthetic.main.layout_fragment_01.*

/**
 * 时间：2022/4/22 14:43
 * 作者：吕卡
 * 备注：树状分支图的渲染页面
 */

class TreeViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_view)
        initViewPager()
    }

    private fun initViewPager() {
        class Fragment1 : Fragment() {
            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                return layoutInflater.inflate(R.layout.layout_fragment_01, container, false)
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                btn_refresh.setOnClickListener { refreshTreeView() }
            }

            private fun refreshTreeView(){
                val readBytes = assets.open("JSON.json").readBytes()
                val json = String(readBytes)
                val data = Gson().fromJson(json, Response::class.java)

                zoom_layout.removeAllViews()
                zoom_layout.engine.clear()
                zoom_layout.engine.setMaxZoom(0.7f, ZoomApi.TYPE_REAL_ZOOM)
                zoom_layout.engine.setMinZoom(0.7f, ZoomApi.TYPE_REAL_ZOOM)

                val treeView = TreeViewLayout(view!!.context)
                val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                zoom_layout.addView(treeView, layoutParams)

                val adapter = AdapterTreeView()
                adapter.setData()
                treeView.setAdapter(adapter)

                Handler(Looper.getMainLooper()).postDelayed({
                    zoom_layout.engine.setMaxZoom(30f, ZoomApi.TYPE_REAL_ZOOM)
//                    zoom_layout.engine.setMinZoom(0.1f, ZoomApi.TYPE_REAL_ZOOM)
                }, 1000)
            }

        }

        val fragment1 = Fragment1()

        class Fragment2 : Fragment() {
            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                val textView = TextView(context)
                val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                textView.layoutParams = layoutParams
                textView.gravity = Gravity.CENTER
                textView.setText("这是第二个页面")
                textView.setTextColor(Color.RED)
                return textView
            }
        }

        val fragment2 = Fragment2()
        val adapter = object : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount() = 2

            override fun getItem(position: Int): Fragment {
                if (position == 0) return fragment1
                return fragment2
            }
        }
        view_pager.adapter = adapter
    }

}