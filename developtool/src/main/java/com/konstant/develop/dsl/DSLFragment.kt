package com.konstant.develop.dsl

import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.konstant.develop.R
import com.konstant.develop.utils.PermissionRequester
import kotlinx.android.synthetic.main.fragment_dsl.*
import java.io.File

class DSLFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dsl, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_create.setOnClickListener {
            if (TextUtils.isEmpty(et_text.text)) {
                Toast.makeText(requireContext(), "JSON不能为空", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val json = et_text.text.toString()
            DSLRenderResultActivity.startActivity(requireActivity(), json)
        }
        btn_default.setOnClickListener {
            DSLRenderResultActivity.startActivity(requireActivity(), "")
        }
    }
}