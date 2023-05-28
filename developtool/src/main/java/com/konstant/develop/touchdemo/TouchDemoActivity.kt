package com.konstant.develop.touchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.konstant.develop.R
import kotlinx.android.synthetic.main.activity_touch_demo.*

class TouchDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_demo)
        tv_demo.setOnClickListener {
            Log.d("TouchGroup","点击了")
            Toast.makeText(this,"点击了",Toast.LENGTH_LONG).show()
        }
    }
}