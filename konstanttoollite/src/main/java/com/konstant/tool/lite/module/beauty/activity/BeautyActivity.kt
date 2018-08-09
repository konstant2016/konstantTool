package com.konstant.tool.lite.module.beauty.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.beauty.adapter.AdapterBeauty
import com.konstant.tool.lite.module.beauty.BeautyService
import com.konstant.tool.lite.network.NetworkUtil
import com.konstant.tool.lite.util.FileUtil
import com.konstant.tool.lite.view.KonstantDialog
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_beauty.*
import kotlinx.android.synthetic.main.layout_dialog_progress.*
import kotlinx.android.synthetic.main.title_layout.*
import org.json.JSONObject
import java.util.concurrent.Executors

/**
 * 描述:美图列表页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */

@SuppressLint("MissingSuperCall")
class BeautyActivity : BaseActivity() {

    private val mUrlList = ArrayList<String>()
    private val mBaseUrl = "https://hot.browser.miui.com/opg-api/item?gid="
    private var mPageIndex = (Math.random() * 1000 + 2807480).toInt()
    private var isPullDown = true
    private val mAdapter by lazy { AdapterBeauty(mUrlList, this) }
    private val dialog by lazy { KonstantDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty)
        setTitle("美女图片")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // recyclerView
        recycler_beauty.apply {
            layoutManager = LinearLayoutManager(this@BeautyActivity)
            addItemDecoration(DividerItemDecoration(this@BeautyActivity, DividerItemDecoration.VERTICAL))
            adapter = mAdapter
        }

        // 刷新监听
        refresh_layout.apply {
            setHeaderView(BezierLayout(this@BeautyActivity))
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                    isPullDown = true
                    mPageIndex -= 2
                    getData()
                }

                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    isPullDown = false
                    mPageIndex += 2
                    getData()
                }
            })
            //启动刷新
            startRefresh()
        }

        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                KonstantDialog(this@BeautyActivity)
                        .setMessage("批量保存到本地？")
                        .setPositiveListener {
                            it.dismiss()
                            requestPermission()
                        }
                        .createDialog()
            }
        }
    }


    // 获取网络数据
    private fun getData() {
        BeautyService.getBeautyData(mBaseUrl + mPageIndex) { state, array ->
            Log.i("MIUI图片", String(array))
            if (!state || array.size < 150) {
                mPageIndex += (Math.random() * 8 - 4).toInt()
                getData()
                return@getBeautyData
            }
            if (!isDestroyed) refreshUI(parseUrlList(String(array)))
        }
    }

    // 手动解析，整合数据
    private fun parseUrlList(string: String): List<String> {
        val urlList = ArrayList<String>()
        val obj = JSONObject(string)
        val array = obj.optJSONArray("data")

        if (array != null) {
            (0 until array.length()).map {
                urlList.add(array.optJSONObject(it).optString("url"))
            }
        }

        return urlList
    }

    // 刷新界面
    private fun refreshUI(urlList: List<String>) {
        Log.i("beauty", "开始刷新界面")
        runOnUiThread {
            if (isPullDown) {
                mUrlList.clear()
                refresh_layout.finishRefreshing()
            } else {
                refresh_layout.finishLoadmore()
            }
            mUrlList.addAll(urlList)
            mAdapter.notifyDataSetChanged()
        }
    }

    // 申请权限
    private fun requestPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onDenied { showToast("你拒绝了权限申请") }
                .onGranted {
                    beachSavePic()
                }
                .start()
    }

    // 批量保存图片
    private fun beachSavePic() {

        if (mUrlList.isEmpty()) {
            showToast("图片列表为空哦~")
            return
        }

        val view = layoutInflater.inflate(R.layout.layout_dialog_progress, null)
        val text = view.findViewById(R.id.text_progress) as TextView
        val progress = view.findViewById(R.id.progress_horizontal) as ProgressBar
        progress.max = mUrlList.size

        dialog.apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            hideNavigation()
            addView(view)
            createDialog()
        }

        Executors.newSingleThreadExecutor().execute {
            mUrlList.forEachIndexed { index, s ->
                runOnUiThread {
                    text.text = "正在保存中(${index + 1}/${mUrlList.size})"
                    progress.progress = index + 1
                }
                Thread.sleep(100)
                val split = s.split("/")
                val name = split[split.size - 1]
                NetworkUtil.get(s) { _, data ->
                    FileUtil.saveBitmapToAlbum(data, name = name)
                }
                if (index == mUrlList.size - 1) {
                    runOnUiThread { dialog.dismiss() }
                    showToast("保存完毕")
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
