package com.konstant.develop

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.konstant.develop.utils.WindowInsertHelper
import kotlinx.android.synthetic.main.activity_status_bar.*

class StatusBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar)

        var statusIconLight = false
        btn_status_color.setOnCheckedChangeListener { _, isChecked ->
            statusIconLight = isChecked
            WindowInsertHelper.setStatusBarLight(window, statusIconLight)
        }

        var staturBg = Color.GREEN
        btn_status_bg.setOnCheckedChangeListener { _, isChecked ->
            staturBg = if (isChecked) Color.GREEN else Color.BLACK
            WindowInsertHelper.setStatusBarBackgroundColor(window, staturBg)
        }

        var navigationColor = false
        btn_navigation_color.setOnCheckedChangeListener { _, isChecked ->
            navigationColor = isChecked
            WindowInsertHelper.setNavigationBarLight(window, navigationColor)
        }

        var navigationbgColor = Color.GREEN
        btn_navigation_bg.setOnCheckedChangeListener { _, isChecked ->
            navigationbgColor = if (isChecked) Color.GREEN else Color.BLACK
            WindowInsertHelper.setNavigationBarBackgroundColor(window, navigationbgColor)
        }

        var full = false
        btn_full_screen.setOnCheckedChangeListener { _, isChecked ->
            full = !isChecked
            WindowInsertHelper.showWindowControl(window, full, full)
        }

        var statusBarDraw = false
        var navigationDraw = false
        btn_draw_status.setOnCheckedChangeListener { _, isChecked ->
            statusBarDraw = isChecked
            WindowInsertHelper.setInvadeSystemBar(window, statusBarDraw, navigationDraw)
        }

        btn_draw_navigation.setOnCheckedChangeListener { _, isChecked ->
            navigationDraw = isChecked
            WindowInsertHelper.setInvadeSystemBar(window, statusBarDraw, navigationDraw)
        }

        btn_status_height.setOnClickListener {
            val statusBarHeight = WindowInsertHelper.getStatusBarHeight(window)
            Toast.makeText(this, "高度:$statusBarHeight", Toast.LENGTH_LONG).show()
        }

        btn_navigation_height.setOnClickListener {
            val navigationBarHeight = WindowInsertHelper.getNavigationBarHeight(window)
            Toast.makeText(this, "高度:$navigationBarHeight", Toast.LENGTH_LONG).show()
        }

    }
}