package com.konstant.video.debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konstant.video.debug.util.ControlLayout
import com.konstant.video.debug.util.NotchModeUtil
import com.konstant.video.debug.util.VideoConstant
import com.konstant.video.debug.util.VideoPlayerUtils
import kotlinx.android.synthetic.main.activity_exo_player.*

class ExoPlayerActivity : AppCompatActivity() {

    private val url by lazy { intent.getStringExtra(VideoConstant.KEY_URL)?:"" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        NotchModeUtil.adapterNotchMode(this, listOf(player_view))
        VideoPlayerUtils.adaptToFullScreen(window)
        initBaseViews()
        player_view.playUrl(url)
    }

    override fun finish() {
        player_view.player?.release()
        super.finish()
    }

    private fun initBaseViews() {
        control_view.setControlListener(object : ControlLayout.ControlListener {
            override fun onPlayStateChanged(playing: Boolean) {
                if (playing) {
                    player_view.player?.pause()
                } else {
                    player_view.player?.play()
                }
            }

            override fun onBackPressed() {
                finish()
            }

            override fun onSeekBarChanged(total: Int, position: Int) {
                val player = player_view.player ?: return
                val targetPosition = (position.toFloat() / total * player.duration).toLong()
                player.seekTo(targetPosition)
            }

            override fun getVideoTotal(): Long {
                return player_view.player?.duration ?: 0L
            }

            override fun getVideoPosition(): Long {
                return player_view.player?.contentPosition ?: 0L
            }

        })
    }

}