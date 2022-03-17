
package com.konstant.develop.dsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstant.develop.R
import kotlinx.android.synthetic.main.fragment_transfer.*

/**
 * 时间：2022/2/15 6:21 下午
 * 作者：吕卡
 * 备注：J2V8引擎的使用
 */

class TransferFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transfer,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_object.setOnClickListener {
            J2V8Transfer().injectObject()
        }
        btn_method.setOnClickListener {
            J2V8Transfer().methodCallback()
        }
        btn_file.setOnClickListener {
            J2V8Transfer().loadJsFile(context)
        }
    }

}