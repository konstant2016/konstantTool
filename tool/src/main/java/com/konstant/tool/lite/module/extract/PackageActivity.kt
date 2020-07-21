package com.konstant.tool.lite.module.extract

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantDialog
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_package_export.*
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

    private var mIsChecked = false
    private val mAppList = ArrayList<AppData>()
    private val mFilterList = ArrayList<AppData>()
    private val mAdapter = AdapterPackage(mFilterList)
    private val mPath by lazy { getExternalFilesDir(null)?.path + File.separator + "apks" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_export)
        setTitle(getString(R.string.package_title))
        initBaseViews()
        readAppList()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        hideSoftKeyboard()
        with(recycler_view) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@PackageActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, position ->
            val result = PackagePresenter.startApp(this, mFilterList[position])
            if (!result) {
                showToast(getString(R.string.package_cannot_start))
            }
        }
        mAdapter.setOnItemLongClickListener { _, position ->
            val packageInfo = (mFilterList[position])
            val itemList = listOf(getString(R.string.package_export), getString(R.string.package_export_share))
            KonstantDialog(this)
                    .setItemList(itemList)
                    .hideNavigation()
                    .setOnItemClickListener { dialog, itemIndex ->
                        dialog.dismiss()
                        when (itemIndex) {
                            0 -> backupApp(packageInfo)
                            1 -> shareSingleApp(packageInfo)
                        }
                    }
                    .setPositiveListener {
                        it.dismiss()
                        backupSingleApp(packageInfo) { status, _ ->
                            val msg = if (status) getString(R.string.package_export_success) else getString(R.string.package_export_fail)
                            showToast(msg)
                        }
                    }
                    .createDialog()
        }
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMorePressed() }
        }
        package_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mFilterList.clear()
                if (s.isNullOrEmpty()) {
                    mFilterList.addAll(mAppList)
                } else {
                    val list = mAppList.filter { it.appName.contains(s.toString(), true) }
                    mFilterList.addAll(list)
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
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
        package_filter.setText("")
        showLoading(true, getString(R.string.package_app_scanning))
        PackagePresenter.getAppList(withSystem, this) {
            runOnUiThread {
                mAppList.clear()
                mAppList.addAll(it)
                mFilterList.clear()
                mFilterList.addAll(it)
                mAdapter.notifyDataSetChanged()
                showLoading(false)
            }
        }
    }

    // 备份应用到指定位置
    private fun backupSingleApp(appData: AppData, result: (Boolean, File) -> Unit) {
        val view = layoutInflater.inflate(R.layout.layout_progress, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .setMessage(getString(R.string.package_app_exporting))
                .hideNavigation()
                .createDialog()

        PackagePresenter.backApp(mPath, appData) { status, file ->
            runOnUiThread { dialog.dismiss() }
            result.invoke(status, file)
        }
    }

    private fun backupApp(appData: AppData) {
        backupSingleApp(appData) { status, _ ->
            val msg = if (status) getString(R.string.package_export_success) else getString(R.string.package_export_fail)
            showToast(msg)
        }
    }

    /**
     * 分享应用
     * 先备份，然后再调用系统分享面板进行分享
     */
    private fun shareSingleApp(appData: AppData) {
        backupSingleApp(appData) { status, file ->
            if (!status) {
                showToast(getString(R.string.package_export_fail))
                return@backupSingleApp
            }
            val fileUri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            val intent = ShareCompat.IntentBuilder.from(this)
                    .setType("application/apk")
                    .intent
            intent.putExtra(Intent.EXTRA_STREAM, fileUri)
            startActivity(intent)
        }
    }

    private fun backupAllApp() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_progress, null)
        view.progress_horizontal.max = mFilterList.size
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
        view.text_progress.text = "${getString(R.string.package_app_exporting)}($int/${mFilterList.size})"
        view.progress_horizontal.progress = index
        PackagePresenter.backApp(mPath, mFilterList[index]) { _, _ ->
            runOnUiThread {
                if (int == mFilterList.size) {
                    dialog.dismiss()
                    return@runOnUiThread
                }
                backApp(int, view, dialog)
            }
        }
    }
}
