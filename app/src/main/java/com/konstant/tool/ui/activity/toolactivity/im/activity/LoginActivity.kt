package com.konstant.tool.ui.activity.toolactivity.im.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import com.konstant.tool.tools.FileUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val TAG_AUTO_LOGIN = "TAG_AUTO_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readLoginState()
        setContentView(R.layout.activity_login)
        setTitle("即时通讯账号登陆")
        initBaseViews()
    }

    // 检查是否自动登陆
    private fun readLoginState() {
        val b = FileUtils.readDataWithSharedPreference(this, TAG_AUTO_LOGIN, false)
        if (b) {
            startActivity(Intent(this, IMActivity::class.java))
            this.finish()
        }
    }

    // 初始化控件
    override fun initBaseViews() {
        super.initBaseViews()

        // 注册按钮的点击事件
        btn_register.setOnClickListener { onRegister() }

        // 登陆按钮的点击事件
        btn_login.setOnClickListener { onLogin() }
    }

    // 注册
    private fun onRegister() {
        AlertDialog.Builder(this)
                .setTitle("提示:")
                .setMessage("暂不开放注册功能，用户名和密码需要联系管理员获取")
                .setPositiveButton("确定") { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
                .create().show()
    }

    // 登陆
    private fun onLogin() {
        if (TextUtils.isEmpty(et_name.text)) {
            Toast.makeText(this, "记得输入用户名", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(et_password.text)) {
            Toast.makeText(this, "记得输入密码", Toast.LENGTH_SHORT).show()
            return
        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("登陆中。。。")
        progressDialog.show()
        EMClient.getInstance().login(et_name.text.toString(), et_password.text.toString(), object : EMCallBack {
            override fun onSuccess() {
                EMClient.getInstance().chatManager().loadAllConversations()
                EMClient.getInstance().groupManager().loadAllGroups()
                startActivity(Intent(this@LoginActivity, IMActivity::class.java))
                FileUtils.saveDataWithSharedPreference(this@LoginActivity, TAG_AUTO_LOGIN, true)
                this@LoginActivity.finish()
            }

            override fun onProgress(progress: Int, status: String?) {

            }

            override fun onError(code: Int, error: String?) {
                showToast("登陆出错：错误码：$code，详情：$error")
            }
        })
    }

    // 弹吐司
    private fun showToast(string: String) {
        runOnUiThread { Toast.makeText(this, string, Toast.LENGTH_SHORT).show() }
    }
}
