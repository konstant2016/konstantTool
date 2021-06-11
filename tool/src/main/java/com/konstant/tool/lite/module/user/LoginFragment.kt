package com.konstant.tool.lite.module.user

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import kotlinx.android.synthetic.main.user_fragment_login.*

/**
 * 时间：6/11/21 3:50 PM
 * 作者：吕卡
 * 备注：登录页面
 */

class LoginFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if (TextUtils.isEmpty(et_account.text)) {
                showToast("记得填写账号哦")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_psd.text)) {
                showToast("记得填写密码哦")
                return@setOnClickListener
            }
        }
    }

    /**
     * 账号登录
     */
    private fun login(){

    }

    /**
     * 同步用户数据并保存到本地
     */
    private fun getUserInfo(){

    }

}