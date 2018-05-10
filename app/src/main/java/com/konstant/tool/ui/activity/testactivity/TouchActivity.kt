package com.konstant.tool.ui.activity.testactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.konstant.tool.R
import kotlinx.android.synthetic.main.activity_touch.*

class TouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)

        btn_2.setOnClickListener { touch.setTOW() }

        btn_3.setOnClickListener { touch.setThree() }

        btn_red.setOnClickListener { touch.setRed() }

        btn_yellow.setOnClickListener { touch.setYellow() }

    }

}
