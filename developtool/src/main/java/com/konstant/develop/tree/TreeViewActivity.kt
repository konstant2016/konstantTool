package com.konstant.develop.tree

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tree_view.*

/**
 * 时间：2022/4/22 14:43
 * 作者：吕卡
 * 备注：树状分支图的渲染页面
 */

class TreeViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_view)

        val readBytes = assets.open("JSON.json").readBytes()
        val json = String(readBytes)
        val token = object : TypeToken<List<Response>>() {}.type
        val list = Gson().fromJson<List<Response>>(json, token)
        val adapter = TreeViewAdapter()
        adapter.setDataList(list)
        treeViewGroup.setAdapter(adapter)
    }

}