package com.konstant.develop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.hz.hzlib.HZLib
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        HZLib.init(this, object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d("PadMsgManager", "收到航智云SDK的消息：${msg.what}--${msg.data}")
            }
        })
        btn_custom.setOnClickListener {
            HZLib.getInstance().getCustomerName()
        }
        btn_sn.setOnClickListener {
            HZLib.getInstance().getSN()
        }
    }
}