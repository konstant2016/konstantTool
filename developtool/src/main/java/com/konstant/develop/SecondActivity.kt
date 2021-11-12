package com.konstant.develop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setFullScreenStatusBarLightMode(this)
        setContentView(R.layout.activity_second)

        btn_dialog.setOnClickListener {
            CustomDialog.showDialog(this)
        }
    }
}