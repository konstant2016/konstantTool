package com.konstant.tool.lite.module.wallpaper

import android.Manifest
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.BaseFragmentAdapter
import com.konstant.tool.lite.util.PermissionRequester
import kotlinx.android.synthetic.main.activity_transparent_wallpaper.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 作者：konstant
 * 时间：2019/10/25 17:23
 * 描述：透明壁纸的实现
 */

class WallpaperActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_wallpaper)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setSegmentalTitle("透明壁纸", "全局壁纸")
        val list = listOf(TransparentWallpaperFragment.newInstance(), FloatWallpaperFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)
    }

}
