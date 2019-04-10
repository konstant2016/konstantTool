package com.konstant.test

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

@RequiresApi(Build.VERSION_CODES.M)
class SecondActivity : AppCompatActivity() {

    val manager by lazy { getCameraManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        requestPermission(android.Manifest.permission.CAMERA)
    }

    private fun getCameraManager(): CameraManager {
        return getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private fun requestPermission(permission: String) {
        if (PackageManager.PERMISSION_DENIED == checkSelfPermission(permission)) {
            requestPermissions(arrayOf(permission), 100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btn_check.setOnCheckedChangeListener { _, isChecked ->
                manager.setTorchMode("0", isChecked)
            }
        }
    }

}
