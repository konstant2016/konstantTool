package com.konstant.tool.lite.module.extract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantDialog
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_dialog_progress.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recycler_view)
        setTitle(getString(R.string.package_title))
        initBaseViews()
        readAppList()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        with(recycler_main) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@PackageActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, position ->
            val result = PackagePresenter.startApp(this, mList[position])
            if (!result) {
                showToast(getString(R.string.package_cannot_start))
            }
        }
        mAdapter.setOnItemLongClickListener { _, position ->
            val packageInfo = (mList[position])
            KonstantDialog(this)
                    .setMessage(getString(R.string.package_backup_app))
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
        val popupWindow = KonstantPopupWindow(this@PackageActivity)
        with(LayoutInflater.from(this).inflate(R.layout.pop_package, null)) {
            checkbox.isChecked = mIsChecked
            layout_filter.setOnClickListener { checkbox.isChecked = !checkbox.isChecked }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                mIsChecked = isChecked
                readAppList(mIsChecked)
                popupWindow.dismiss()
            }
            tv_all_save.setOnClickListener {
                popupWindow.dismiss()
                KonstantDialog(this@PackageActivity)
                        .setMessage(getString(R.string.package_save_all_to_local))
                        .setPositiveListener {
                            it.dismiss()
                            backupAllApp()
                        }
                        .createDialog()
            }
            popupWindow.addView(this).showAsDropDown(title_bar)
        }
    }

    private fun readAppList(withSystem: Boolean = false) {
        showLoading(true, getString(R.string.package_app_scanning))
        PackagePresenter.getAppList(withSystem, this) {
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
                .setMessage(getString(R.string.package_app_exporting))
                .hideNavigation()
                .createDialog()

        PackagePresenter.backApp(mPath, appData) {
            runOnUiThread { dialog.dismiss() }
            val msg = if (it) getString(R.string.package_export_success) else getString(R.string.package_export_fail)
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
        view.text_progress.text = "${getString(R.string.package_app_exporting)}($int/${mList.size})"
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
