package com.konstant.tool.lite.module.express.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.express.adapter.AdapterExpressList
import com.konstant.tool.lite.module.express.ExpressManager
import com.konstant.tool.lite.module.express.param.ExpressChanged
import com.konstant.tool.lite.data.express.ExpressData
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_express.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 描述:物流列表页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */

@SuppressLint("MissingSuperCall")
class ExpressListActivity : BaseActivity() {

    private val expressList = ArrayList<ExpressData>()
    private val mAdapter by lazy { AdapterExpressList(this, expressList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express)
        setTitle("物流查询")
        readLocalExpress()
        initBaseViews()
        updateUI()
    }

    // 初始化基础控件
    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE

        listview_express.adapter = mAdapter

        img_more.setOnClickListener { addExpress() }

        listview_express.setOnItemClickListener { _, _, position, _ ->
            val data = expressList[position]
            expressQuery(data.number, data.name)
        }

        listview_express.setOnItemLongClickListener { _, _, position, _ ->
            KonstantDialog(this)
                    .setMessage("是否删除此记录？")
                    .setPositiveListener {
                        it.dismiss()
                        val data = expressList[position]
                        ExpressManager.deleteExpress(data)
                        readLocalExpress()
                        updateUI()
                    }
                    .createDialog()
            true
        }
    }

    // 更新界面
    private fun updateUI() {
        if (expressList.isEmpty()) {
            tv_tips.visibility = View.VISIBLE
            listview_express.visibility = View.GONE
        } else {
            tv_tips.visibility = View.GONE
            listview_express.visibility = View.VISIBLE
        }
    }

    // 读取本地保存的物流信息
    private fun readLocalExpress() {
        expressList.apply {
            clear()
            addAll(ExpressManager.readExpress())
            mAdapter.notifyDataSetChanged()
        }
    }


    // 添加物流查询
    private fun addExpress() {
        val viewDialog = layoutInflater.inflate(R.layout.pop_express_list, null)
        val etNumber = viewDialog.findViewById(R.id.et_num) as EditText
        val etName = viewDialog.findViewById(R.id.et_remark) as EditText

        KonstantDialog(this)
                .setMessage("添加物流信息")
                .addView(viewDialog)
                .setPositiveListener {
                    var name = "保密物件"
                    if (!TextUtils.isEmpty(etName.text)) {
                        name = etName.text.toString()
                    }
                    if (TextUtils.isEmpty(etNumber.text)) {
                        showToast("记得输入运单号哦")
                        return@setPositiveListener
                    }
                    it.dismiss()
                    expressQuery(etNumber.text.toString(), name)
                }
                .createDialog()
        showKeyboard(etNumber)
    }

    // 跳转到快递查询页面
    private fun expressQuery(num: String, name: String) {
        val intent = Intent(this, ExpressDetailActivity::class.java)
        intent.putExtra("number", num)
        intent.putExtra("name", name)
        startActivity(intent)
    }


    // 物流状态发生了变化
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onExpressChanged(msg: ExpressChanged) {
        readLocalExpress()
        updateUI()
    }

    override fun onDestroy() {
        ExpressManager.onDestroy(this)
        super.onDestroy()
    }
}
