package com.konstant.develop.debug

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konstant.develop.R
import kotlinx.android.synthetic.main.activity_page_start_debug.*

class PageStartDebugActivity : AppCompatActivity() {

    private val fragment1 = PageStartFragment(Color.RED)
    private val fragment2 = PageStartFragment(Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_start_debug)

        supportFragmentManager.beginTransaction().add(R.id.view_group, fragment1)
            .hide(fragment1)
            .add(R.id.view_group, fragment2)
            .commitAllowingStateLoss()

        btn_left.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(fragment2)
                .show(fragment1)
                .commitAllowingStateLoss()
        }
        btn_right.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(fragment1)
                .show(fragment2)
                .commitAllowingStateLoss()
        }
    }
}