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
        setTitle("高级设置")
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
        KonstantDialog(this).setTitle("确定删除此条规则？")
                .setPositiveListener {
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
                .setMessage("添加自定义规则")
                .addView(viewDialog)
                .setPositiveListener {
                    if (!TextUtils.isEmpty(etPackage.text)) {
                        showToast("包名不能为空")
                        return@setPositiveListener
                    }
                    if (TextUtils.isEmpty(etClass.text)) {
                        showToast("类名不能为空")
                        return@setPositiveListener
                    }
                    if (TextUtils.isEmpty(etResource.text)) {
                        showToast("资源Id不能为空")
                        return@setPositiveListener
                    }
                    val position = mCustomRules.size
                    val customRule = AutoSkipManager.CustomRule(etPackage.toString(), etClass.toString(), etResource.toString())
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