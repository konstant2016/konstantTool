package com.konstant.konstanttools.ui.activity.toolactivity.traffic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jiechic.library.android.snappy.Snappy
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow.*

/**
 * 流量统计
 */

class TrafficActivity : BaseActivity() {

    // 网络状态发生变化
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo == null || (!networkInfo.isConnected)) {
                Toast.makeText(context, "没有网络连接", Toast.LENGTH_SHORT).show()
                return
            }
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(context, "WIFI网络", Toast.LENGTH_SHORT).show()
                return
            }
            if (networkInfo.type == ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(context, "手机网络", Toast.LENGTH_SHORT).show()
                return
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
        setTitle("流量统计服务")
        initBaseViews()
        registerReceiver()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        val intent = Intent(this, TrafficService::class.java)
        intent.putExtra("countGap", 5000L)
        intent.putExtra("uploadGap", 10000L)

        btn_start.setOnClickListener { startService(intent) }
        btn_stop.setOnClickListener { stopService(intent) }

        btn_send.setOnClickListener { sendToServer() }

        btn_snappy.setOnClickListener {
            val s: ByteArray = SnappyTool.compressString("HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD")
            Toast.makeText(this, s.toString(Charsets.UTF_8), Toast.LENGTH_SHORT).show()
            Log.d("解开", SnappyTool.uncompressString(s))
        }
    }

    // 测试发送到服务器中去
    private fun sendToServer() {
        val s: ByteArray = SnappyTool.compressString("HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD")
        Upload.getInstance().uploadByteArray(s) { state, data ->
            Log.d("上传到服务器的结果", "结果：$state,$data")
        }
    }

    // 注册网络监听服务
    private fun registerReceiver(){
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(mReceiver,filter)
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }

}
