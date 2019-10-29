package com.konstant.tool.lite.module.wallpaper

import android.hardware.Camera
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

class TransparentWallpaperService : WallpaperService() {

    private var camera: Camera? = null

    override fun onCreateEngine(): Engine {
        return CameraEngine()
    }

    inner class CameraEngine : Engine(), Camera.PreviewCallback {
        override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
            camera?.addCallbackBuffer(data)
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            startPreview()
        }

        fun startPreview() {
            if (camera == null) camera = Camera.open()
            camera?.run {
                setDisplayOrientation(90)
                setPreviewDisplay(surfaceHolder)
                startPreview()
            }

        }

        fun stopPreview() {
            camera?.run {
                stopPreview()
                setPreviewDisplay(null)
                release()
            }
            camera = null
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                startPreview()
            } else {
                stopPreview()
            }
        }
    }

}
