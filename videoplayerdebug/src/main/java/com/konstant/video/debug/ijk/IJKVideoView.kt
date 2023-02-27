package com.konstant.video.debug.ijk

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.util.concurrent.atomic.AtomicBoolean

class IJKVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mMediaPlayer: IMediaPlayer? = null
    private val surfaceCreated = AtomicBoolean(false)
    private val blockList = mutableListOf<() -> Unit>()
    private var mSurface: Surface? = null


    init {
        val textureView = TextureView(context)
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                mSurface = Surface(surface)
                surfaceCreated.set(true)
                blockList.forEach { it.invoke() }
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                surface.release()
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
        this.addView(textureView, layoutParams)
    }

    fun playUrl(url: String){
        mMediaPlayer?.let {
            it.stop()
            it.release()
        }
        blockTask {
            val player = IjkMediaPlayer()
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist", "crypto,file,http,https,tcp,tls,udp")
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);

            mMediaPlayer = player
            mMediaPlayer?.dataSource = url
            //给mediaPlayer设置视图
            mMediaPlayer?.setSurface(mSurface)
            mMediaPlayer?.prepareAsync()
            mMediaPlayer?.start()
        }
    }

    fun getMediaPlayer() = mMediaPlayer

    private fun blockTask(block: () -> Unit) {
        if (surfaceCreated.get()) {
            block.invoke()
            return
        }
        blockList.clear()
        blockList.add(block)
    }

}