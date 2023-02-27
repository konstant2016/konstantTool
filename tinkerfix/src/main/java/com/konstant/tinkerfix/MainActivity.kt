package com.konstant.tinkerfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tencent.tinker.lib.tinker.TinkerInstaller

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), "");
    }
}