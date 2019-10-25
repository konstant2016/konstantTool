package com.konstant.tool.lite.module.wallpaper

import android.Manifest
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.PermissionRequester
import kotlinx.android.synthetic.main.activity_transparent_wallpaper.*

/**
 * 作者：konstant
 * 时间：2019/10/25 17:23
 * 描述：透明壁纸的实现
 */

class TransparentWallpaperActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_wallpaper)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        btn_enable.setOnClickListener {
            PermissionRequester.requestPermission(this,
                    mutableListOf(Manifest.permission.CAMERA),
                    { startCustomWallpaperPicker() },
                    { showToast("设置透明壁纸需要获取摄像头权限") })
        }
    }

    // 启用系统的壁纸选择器
    private fun startSystemWallpaperPicker() {
        val intent = Intent.createChooser(Intent(Intent.ACTION_SET_WALLPAPER), "设置透明壁纸")
        startActivity(intent)
    }

    // 启用指定的壁纸选择器
    private fun startCustomWallpaperPicker() {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, TransparentWallpaperService::class.java));
        startActivity(intent)
    }
}
