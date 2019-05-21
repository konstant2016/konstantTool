package com.konstant.tool.lite.module.extract

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_package.*
import kotlinx.android.synthetic.main.layout_dialog_progress.view.*
import kotlinx.android.synthetic.main.pop_package.view.*
import kotlinx.android.synthetic.main.title_layout.*
import java.io.File

/**
 * 时间：2019/4/19 14:22
 * 创建：吕卡
 * 描述：安装包提取
 */

class PackageActivity : BaseActivity() {

    private var mIsChecked = false;
    private val mList = ArrayList<AppData>()
    private val mAdapter = AdapterPackage(mList)
    private val mPath by lazy { getExternalFilesDir(null)?.path + File.separator + "apks" }
    private lateinit var mPop: PopupWindow

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
            val result = PackagePresenter.startApp(this, mList[position])
            if (!result) {
                showToast("此应用不支持跳转")
            }
        }
        mAdapter.setOnItemLongClickListener { _, position ->
            val packageInfo = (mList[position])
            KonstantDialog(this)
                    .setMessage("备份此应用？")
                    .setPositiveListener {
                        it.dismiss()
                        backupSingleApp(packageInfo)
                    }
                    .createDialog()
        }
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMorePressed() }
        }
    }

    private fun onMorePressed() {
        with(LayoutInflater.from(this).inflate(R.layout.pop_package, null)) {
            checkbox.isChecked = mIsChecked

            layout_filter.setOnClickListener { checkbox.isChecked = !checkbox.isChecked }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                mIsChecked = isChecked
                mPop.dismiss()
                readAppList(mIsChecked)
            }

            tv_all_save.setOnClickListener {
                mPop.dismiss()
                KonstantDialog(this@PackageActivity)
                        .setMessage("批量保存到本地？")
                        .setPositiveListener {
                            it.dismiss()
                            backupAllApp()
                        }
                        .createDialog()
            }
            mPop = PopupWindow(this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
            mPop.showAsDropDown(title_bar)
        }
    }

    private fun readAppList(withSystem: Boolean = false) {
        showLoading(true,"正在扫描应用...")
        PackagePresenter.getAppList(withSystem,this){
            runOnUiThread {
                mList.clear()
                mList.addAll(it)
                mAdapter.notifyDataSetChanged()
                showLoading(false)
            }
        }
    }

    private fun backupSingleApp(appData: AppData) {
        val view = layoutInflater.inflate(R.layout.layout_progress, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .setMessage("正在提取中...")
                .hideNavigation()
                .createDialog()

        PackagePresenter.backApp(mPath, appData) {
            runOnUiThread { dialog.dismiss() }
            val msg = if (it) "提取成功" else "备份失败，此应用不支持备份"
            showToast(msg)
        }
    }

    private fun backupAllApp() {
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
        PackagePresenter.backApp(mPath, mList[index]) {
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
