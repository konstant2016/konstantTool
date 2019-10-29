package com.konstant.tool.lite.module.wallpaper

import android.Manifest
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.util.PermissionRequester
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.fragment_wallpaper_transparent.*

/**
 * 作者：konstant
 * 时间：2019/10/28 10:28
 */

class TransparentWallpaperFragment : BaseFragment() {

    companion object {
        fun newInstance() = TransparentWallpaperFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallpaper_transparent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_enable.setOnClickListener {
            PermissionRequester.requestPermission(KonApplication.context,
                    mutableListOf(Manifest.permission.CAMERA),
                    { startCustomWallpaperPicker() },
                    { showToast("设置透明壁纸需要获取摄像头权限") })
        }

        btn_disable.setOnClickListener {
            KonstantDialog(mActivity)
                    .setMessage("手动更换桌面壁纸后，透明壁纸即自动关闭")
                    .setPositiveListener { it.dismiss() }
                    .createDialog()
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
                ComponentName(mActivity, TransparentWallpaperService::class.java));
        startActivity(intent)
    }

}