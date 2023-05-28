package com.konstant.develop.contract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.konstant.develop.R

class DemoFragment :Fragment() {

    private val openBranchVideo = this.registerForActivityResult<String, Long>(CustomContract()) { result ->
        val textView = this@DemoFragment.view?.findViewById<TextView>(R.id.btn_result)
        textView?.text = "" + result
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_demo_fragment,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btn_open).setOnClickListener(View.OnClickListener {
            val videoId = ""
            openBranchVideo.launch(videoId)
        })
    }

}