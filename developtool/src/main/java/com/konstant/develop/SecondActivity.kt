package com.konstant.develop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initBaseViews()
    }

    private fun initBaseViews() {
        new_task.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        btn_app.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.application.startActivity(intent)
        }
    }
}