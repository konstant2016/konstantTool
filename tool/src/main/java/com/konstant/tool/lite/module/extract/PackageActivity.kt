package com.konstant.tool.lite.module.extract

import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.ApplicationUtil
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_package.*
import kotlinx.android.synthetic.main.layout_dialog_progress.view.*
import kotlinx.android.synthetic.main.title_layout.*
import java.io.File

/**
 * 时间：2019/4/19 14:22
 * 创建：吕卡
 * 描述：安装包提取
 */

class PackageActivity : BaseActivity() {

    private val mList = ArrayList<PackageInfo>()
    private val mAdapter = AdapterPackage(mList)
    private val mPath by lazy { getExternalFilesDir(null)?.path + File.separator + "apks" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        setTitle("安装包提取")
        initBaseViews()
        readAppList()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        with(layout_recycler) {
            layoutManager = LinearLayoutManager(this@PackageActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, position ->
            val packageName = ApplicationUtil.getPackageName(mList[position])
            if (packageName.isEmpty()) {
                showToast("标识为空，无法跳转！")
                return@setOnItemClickListener
            }
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            val result = startActivitySafely(intent)
            if (!result) {
                showToast("此应用不支持直接跳转~")
            }
        }
        mAdapter.setOnItemLongClickListener { _, position ->
            val packageInfo = (mList[position])
            KonstantDialog(this)
                    .setMessage("备份此应用？")
                    .setPositiveListener {
                        it.dismiss()
                        backupApp(packageInfo)
                    }
                    .createDialog()
        }
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                KonstantDialog(this@PackageActivity)
                        .setMessage("批量保存到本地？")
                        .setPositiveListener {
                            it.dismiss()
                            backAllApp()
                        }
                        .createDialog()
            }
        }
    }

    private fun readAppList() {
        showLoading(state = true)
        Thread {
            ApplicationUtil.getPackageInfoList {
                runOnUiThread {
                    showLoading(state = false)
                    mList.clear()
                    mList.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun backupApp(packageInfo: PackageInfo) {
        val view = layoutInflater.inflate(R.layout.layout_progress, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .setMessage("正在提取中...")
                .hideNavigation()
                .createDialog()

        ApplicationUtil.backUserApp(mPath, packageInfo) {
            runOnUiThread { dialog.dismiss() }
            val msg = if (it) "提取成功" else "备份失败，此应用不支持备份"
            showToast(msg)
        }
    }

    private fun backAllApp() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_progress, null)
        view.progress_horizontal.max = mList.size
        val dialog = KonstantDialog(this)
        with(dialog) {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            hideNavigation()
            addView(view)
            createDialog()
        }
        backApp(0, view, dialog)
    }

    private fun backApp(index: Int, view: View, dialog: KonstantDialog) {
        val int = index + 1
        view.text_progress.text = "提取中($int/${mList.size})"
        view.progress_horizontal.progress = index
        ApplicationUtil.backUserApp(mPath, mList[index]) {
            runOnUiThread {
                if (int == mList.size) {
                    dialog.dismiss()
                    return@runOnUiThread
                }
                backApp(int, view, dialog)
            }
        }
    }
}
