package com.yangcong345.kratos.container

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.yangcong345.context_provider.ContextProvider
import com.yangcong345.kratos.R

/**
 * 时间：2022/2/21 5:16 下午
 * 作者：吕卡
 * 备注：动态化项目的根Activity，主要逻辑都在KratosFragment中
 */

class KratosActivity : AppCompatActivity() {

    companion object {
        private const val KEY_URL = "url"

        fun startKratosActivity(context: Context, url: String) {
            if (TextUtils.isEmpty(url)) {
                throw IllegalArgumentException("url 不能为空！")
            }
            val intent = Intent(context, KratosActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtra(KEY_URL, url)
            context.startActivity(intent)
        }
    }

    private val mFragment by lazy {
        KratosFragment.getInstance(intent.getStringExtra(KEY_URL) ?: "1431381")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextProvider.setApplication(application)
        setContentView(R.layout.activity_kratos)
        supportFragmentManager.beginTransaction().replace(R.id.layout_fragment, mFragment).commitAllowingStateLoss()
    }

    /**
     * 由Fragment来处理返回事件
     */
    override fun onBackPressed() {
        if (mFragment.onBackPressed()) return
        super.onBackPressed()
    }

    /**
     * 回调结果返回给Fragment处理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFragment.onActivityResult(requestCode, resultCode, data)
    }

}