package com.konstant.tool.lite.module.skip

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_auto_skip_advance.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述：自动跳过的高级设置
 * 创建者：吕卡
 * 时间：2020/7/24:5:42 PM
 */
class AutoSkipAdvanceActivity : BaseActivity() {

    private val mCustomRules = AutoSkipManager.getCustomRules()
    private val mAdapter by lazy { AdapterAdvanceSkip(mCustomRules) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_skip_advance)
        setTitle(getString(R.string.skip_advance_setting))
        initViews()
    }

    private fun initViews() {
        updateViewStatus()
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener {
            showAddDialog()
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mAdapter
        mAdapter.setOnItemLongClickListener { _, position ->
            showRemoveDialog(position)
        }
    }

    private fun updateViewStatus(){
        if (mCustomRules.isEmpty()) {
            recycler_view.visibility = View.GONE
            tv_empty.visibility = View.VISIBLE
        }else{
            recycler_view.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
        }
    }

    private fun showRemoveDialog(position: Int) {
        KonstantDialog(this).setTitle(getString(R.string.skip_delete_title))
                .setPositiveListener {
                    it.dismiss()
                    AutoSkipManager.removeRules(mCustomRules[position])
                    mAdapter.notifyItemRemoved(position)
                    updateViewStatus()
                }
                .createDialog()
    }

    private fun showAddDialog() {
        val viewDialog = layoutInflater.inflate(R.layout.pop_advance_skip_list, null)
        val etPackage = viewDialog.findViewById(R.id.et_package) as EditText
        val etClass = viewDialog.findViewById(R.id.et_class) as EditText
        val etResource = viewDialog.findViewById(R.id.et_resource) as EditText

        KonstantDialog(this)
                .setMessage(getString(R.string.skip_dialog_add_title))
                .addView(viewDialog)
                .setPositiveListener {
                    if (TextUtils.isEmpty(etPackage.text)) {
                        showToast(getString(R.string.skip_toast_package_empty))
                        return@setPositiveListener
                    }
                    if (TextUtils.isEmpty(etClass.text)) {
                        showToast(getString(R.string.skip_toast_class_empty))
                        return@setPositiveListener
                    }
                    if (TextUtils.isEmpty(etResource.text)) {
                        showToast(getString(R.string.skip_toast_resource_empty))
                        return@setPositiveListener
                    }
                    val position = mCustomRules.size
                    val customRule = AutoSkipManager.CustomRule(etPackage.text.toString(), etClass.text.toString(), etResource.text.toString())
                    AutoSkipManager.addCustomRules(customRule)
                    mAdapter.notifyItemInserted(position)
                    updateViewStatus()
                    it.dismiss()
                }
                .createDialog()
        showKeyboard(etPackage)
    }

    override fun onDestroy() {
        super.onDestroy()
        AutoSkipManager.onDestroy(this)
    }
}