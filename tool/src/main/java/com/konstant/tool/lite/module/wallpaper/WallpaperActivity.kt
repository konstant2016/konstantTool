package com.konstant.tool.lite.module.wallpaper

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.activity_viewpager.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 作者：konstant
 * 时间：2019/10/25 17:23
 * 描述：透明壁纸的实现
 */

class WallpaperActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initViews()
    }

    private fun initViews() {
        setSegmentalTitle(getString(R.string.wallpaper_translate), getString(R.string.wallpaper_float))
        val list = listOf(TransparentWallpaperFragment.newInstance(), FloatWallpaperFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)
    }

}
