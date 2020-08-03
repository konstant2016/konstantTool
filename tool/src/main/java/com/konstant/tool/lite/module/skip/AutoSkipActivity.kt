package com.konstant.tool.lite.module.skip

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.AppUtil
import kotlinx.android.synthetic.main.activity_auto_skip.*

/**
 * 描述：自动跳过开屏广告
 * 创建者：吕卡
 * 时间：2020/7/23:6:02 PM
 */
class AutoSkipActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_skip)
        setTitle(R.string.auto_skip_title)
        initViews()
    }

    private fun initViews() {
        // 总开关
        switch_skip.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
        layout_status.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
        // 跳过提示
        switch_toast.isChecked = AutoSkipManager.getShowToast(this)
        layout_toast.setOnClickListener {
            switch_toast.isChecked = !switch_toast.isChecked
        }
        switch_toast.setOnCheckedChangeListener { _, isChecked ->
            AutoSkipManager.setShowToast(this, isChecked)
        }
        // 模糊匹配
        switch_match.isChecked = AutoSkipManager.getMatch(this)
        layout_match.setOnClickListener {
            switch_match.isChecked = !switch_match.isChecked
        }
        switch_match.setOnCheckedChangeListener { _, isChecked ->
            AutoSkipManager.setMatch(this, isChecked)
        }
        // 电池优化
        layout_battery.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Toast.makeText(this, "此选项仅支持6.0以上设备", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                val powerManager = getSystemService(POWER_SERVICE) as PowerManager
                val batteryIgnore = powerManager.isIgnoringBatteryOptimizations(packageName)
                if (batteryIgnore) {
                    Toast.makeText(this, "电池忽略已启用", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.data = Uri.parse("package:$packageName");
                startActivity(intent)
            } catch (e: Exception) {
                //
            }
        }
        // 应用白名单
        layout_white_list.setOnClickListener {
            val intent = Intent(this, WhiteListActivity::class.java)
            startActivitySafely(intent)
        }
        // 高级设置
        layout_advance.setOnClickListener {
            val intent = Intent(this, AutoSkipAdvanceActivity::class.java)
            startActivitySafely(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val enable = AppUtil.isAccessibilityEnable(this)
        switch_skip.isChecked = enable
        if (enable) {
            val intent = Intent(this, AutoSkipService::class.java)
            startService(intent)
        }
    }
}