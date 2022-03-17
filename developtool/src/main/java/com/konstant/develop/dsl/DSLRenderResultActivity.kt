package com.konstant.develop.dsl

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import com.yangcong345.kratos.render.RenderEngine
import com.yangcong345.kratos.render.ViewEventListener
import kotlinx.android.synthetic.main.activity_dslrender_result.*

class DSLRenderResultActivity : BaseActivity() {

    companion object {
        private var mSource: String = ""
        fun startActivity(activity: FragmentActivity, resource: String) {
            mSource = if (TextUtils.isEmpty(resource)) {
                String(activity.assets.open("共学页面.json").readBytes())
            } else {
                resource
            }
            val intent = Intent(activity, DSLRenderResultActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val mToast by lazy { Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dslrender_result)
        val render = RenderEngine.createRenderEngine()
        render.buildView(this, mSource) {
            layout_parent.addView(it, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }
}