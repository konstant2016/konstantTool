package com.konstant.tool.lite.module.wallpaper

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.util.ImageSelector
import kotlinx.android.synthetic.main.fragment_wallpaper_float.*

/**
 * 作者：konstant
 * 时间：2019/10/28 10:26
 * 描述：全局悬浮壁纸设置
 */

class FloatWallpaperFragment : BaseFragment() {

    private val TAG = "FloatWallpaperFragment"

    companion object {
        fun newInstance() = FloatWallpaperFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallpaper_float, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_seekbar.progress = SettingManager.getWallpaperTransparent(mActivity)
        view_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                SettingManager.saveWallpaperTransparent(mActivity, seekBar.progress)
                FloatWallpaperService.startTransparentWallpaper(mActivity, seekBar.progress)
            }
        })
        btn_enable.setOnClickListener { enableFloatWallpaper(view_seekbar.progress) }
        btn_disable.setOnClickListener { FloatWallpaperService.stopTransparentWallpaper(mActivity) }
    }

    private fun enableFloatWallpaper(transparent: Int = 70) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(mActivity)) {
            showToast("需要开启显示在其他应用的上层权限")
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${Uri.parse(mActivity.packageName)}"))
            mActivity.startActivity(intent)
            return
        }
        ImageSelector.selectImg(mActivity, FloatWallpaperService.WALLPAPER_NAME, 540, 960) {
            Log.d(TAG, "选择图片回调")
            if (!it) {
                showToast("图片未选择")
            } else {
                FloatWallpaperService.startTransparentWallpaper(mActivity, transparent)
            }
        }
    }
}