package com.konstant.tool.lite.module.wallpaper

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.NotificationCreator
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.util.FileUtil

class FloatWallpaperService : Service() {

    companion object {
        val WALLPAPER_NAME = "floatWallpaperName.png"
        val WALLPAPER_TRANSPARENT = "transparent"
        var COMMEND_TYPE = "commendType"

        fun startTransparentWallpaper(context: Context, transparent: Int) {
            val intent = Intent(context, FloatWallpaperService::class.java)
            intent.putExtra(WALLPAPER_TRANSPARENT, transparent)
            intent.putExtra(COMMEND_TYPE, 1)
            context.startService(intent)
        }

        fun stopTransparentWallpaper(context: Context) {
            val intent = Intent(context, FloatWallpaperService::class.java)
            intent.putExtra(COMMEND_TYPE, 2)
            context.startService(intent)
        }
    }

    private val mView by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_float_wallpapewr, null) as ImageView
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val type = intent.getIntExtra(COMMEND_TYPE, 0)
        when (type) {
            1 -> {
                val transparent = intent.getIntExtra(WALLPAPER_TRANSPARENT, 70)
                showFloatWallpaper(transparent)
            }
            2 -> {
                removeFloatWallpaper()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showFloatWallpaper(transparent: Int) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val bitmap = FileUtil.getBitmap(this, WALLPAPER_NAME) ?: return
        SettingManager.saveKillProcess(this, false)
        val resource = getAlplaBitmap(bitmap, transparent)
        mView.setImageBitmap(resource)

        val params = WindowManager.LayoutParams()
                .apply {
                    type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        WindowManager.LayoutParams.TYPE_PHONE
                    }
                    format = PixelFormat.TRANSLUCENT
                    flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  // 不可聚焦
                            or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE    // 不可触摸
                            or WindowManager.LayoutParams.FLAG_FULLSCREEN       // 全屏
                            or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)    // 可放到状态栏上
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.MATCH_PARENT
                }
        removeFloatWallpaper()
        windowManager.addView(mView, params)
        val notification = NotificationCreator.createForegroundNotification(this, msg = getString(R.string.wallpaper_float_create_notification),
                intent = Intent(this, WallpaperActivity::class.java))
        startForeground(1, notification)
    }

    private fun removeFloatWallpaper() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        try {
            windowManager.removeView(mView)
        } catch (e: Exception) {
            //
        }
    }

    private fun getAlplaBitmap(sourceImg: Bitmap, number: Int): Bitmap {
        val argb = IntArray(sourceImg.width * sourceImg.height)
        sourceImg.getPixels(argb, 0, sourceImg.width, 0, 0, sourceImg.width, sourceImg.height)
        val percent = number * 255 / 100
        for (i in argb.indices) {
            argb[i] = percent shl 24 or (argb[i] and 0x00FFFFFF)
        }
        return Bitmap.createBitmap(argb, sourceImg.width, sourceImg.height, Bitmap.Config.ARGB_8888)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }
}
