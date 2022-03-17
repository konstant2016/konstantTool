package com.konstant.develop.yangcong

import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_yang_cong_debug.*

class YangCongDebugActivity : BaseActivity() {

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("YangCongDebugActivity", "收到广播成功")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yang_cong_debug)

        btn_service.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val component = ComponentName("com.yangcong345.android.phone", "com.yangcong345.pad.launcher.PadForegroundService")
                val intent = Intent()
                intent.component = component
                startForegroundService(intent)
            } else {
                val component = ComponentName("com.yangcong345.android.phone", "com.yangcong345.pad.launcher.PadLauncherService")
                val intent = Intent()
                intent.component = component
                startService(intent)
            }
        }

        btn_launcher.setOnClickListener {
            val component = ComponentName("com.yangcong345.android.phone", "com.yangcong345.pad.launcher.PadLauncherActivity")
            val intent = Intent()
            intent.component = component
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        registerBroadCast()
    }

    private fun registerBroadCast() {
        val filter = IntentFilter()
        filter.addAction("com.yangcong345.android.phone.padService.success")
        registerReceiver(mReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

}