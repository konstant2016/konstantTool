package com.yangcong345.kratos.container

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yangcong345.kratos.R
import com.yangcong345.kratos.render.RenderEngine
import com.yangcong345.kratos.resourse.DSLBundle
import com.yangcong345.kratos.resourse.PageResourceCallback
import com.yangcong345.kratos.resourse.ResourceHelper
import com.yangcong345.kratos.utils.FileUtil
import kotlinx.android.synthetic.main.fragment_kratos.*


/**
 * 时间：2022/2/21 5:21 下午
 * 作者：吕卡
 * 备注：动态化项目的 Fragment
 */

class KratosFragment : Fragment() {

    companion object {
        private const val KEY_URL = "url"
        fun getInstance(url: String): KratosFragment {
            val bundle = Bundle()
            bundle.putString(KEY_URL, url)
            val fragment = KratosFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * 处理返回事件
     */
    fun onBackPressed(): Boolean {
        return false
    }

    /**
     * 在这里除了ActivityResult数据
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kratos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ResourceHelper.getDSLPageResource("xxxxxxx", object : PageResourceCallback {
            override fun onStart(pageName: String) {

            }

            override fun onProgress(progress: Int) {

            }

            override fun onFail(e: Throwable) {

            }

            override fun onSuccess(bundle: DSLBundle) {
                val json = FileUtil.getStringFromFile(bundle.dslFile)
                RenderEngine.createRenderEngine().buildView(requireContext(), json) { view ->
                    requireActivity().runOnUiThread {
                        layout_container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    }
                }

            }
        })
    }
}