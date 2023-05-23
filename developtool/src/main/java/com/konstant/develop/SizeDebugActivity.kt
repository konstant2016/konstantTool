package com.konstant.develop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konstant.develop.utils.SizeUtil
import com.konstant.develop.utils.WindowInsertHelper
import kotlinx.android.synthetic.main.activity_size_debug.*

class SizeDebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsertHelper.setInvadeSystemBar(window,true,false)
        NotchModeUtil.setNotchMode(this)
        setContentView(R.layout.activity_size_debug)

        SizeUtil(this,1000,true,true).resetView(view_demo)

        SizeUtil(this,1000,false,true).resetView(view_demo_2)

    }
}