package com.konstant.develop

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_q_r_scan.*

class QRScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_q_r_scan)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        } else {
            initScanView()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initScanView()
        } else {
            Toast.makeText(this, "摄像头权限被禁止", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initScanView() {
        layout_scan.setOnScannerCompletionListener { rawResult, _, _ ->
            if (rawResult == null) {
                Toast.makeText(this, "啥也没扫出来", Toast.LENGTH_LONG).show()
                return@setOnScannerCompletionListener
            }
            showResult(rawResult.toString())
        }
    }

    private fun showResult(string: String) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_show_bottom_to_top)
        layout_result.startAnimation(anim)
        layout_result.visibility = View.VISIBLE
        tv_result.text = string
        btn_jump.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(string))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "没有应用可以响应SCHEME", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        layout_scan.onPause()
    }

    override fun onResume() {
        super.onResume()
        layout_scan.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}