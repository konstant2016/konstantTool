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
import com.konstant.tool.lite.module.setting.SettingManager
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
            PermissionRequester.requestPermission(getNotNullContext(),
                    mutableListOf(Manifest.permission.CAMERA),
                    { startCustomWallpaperPicker() },
                    { showToast(getString(R.string.wallpaper_translate_need_permission)) })
        }

        btn_disable.setOnClickListener {
            KonstantDialog(getNotNullContext())
                    .setMessage(getString(R.string.wallpaper_translate_close_describe))
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
        SettingManager.saveKillProcess(getNotNullContext(), false)
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(getNotNullContext(), TransparentWallpaperService::class.java));
        startActivity(intent)
    }

}