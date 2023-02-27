package com.konstant.video.debug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konstant.video.debug.util.VideoConstant
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val localFile by lazy {
        File(getExternalFilesDir(null), "video_test.mp4")
    }

    private val onLineUrl = "https://hls.media.yangcong345.com/fullHigh/fullHigh_8ff4741e-3b25-4a25-baaf-8f01bb5d77c7.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_exo_local.setOnClickListener {
            releaseFileBlock {
                val intent = Intent(this, ExoPlayerActivity::class.java)
                intent.putExtra(VideoConstant.KEY_URL, localFile.path)
                startActivity(intent)
            }
        }

        btn_exo_online.setOnClickListener {
            val intent = Intent(this, ExoPlayerActivity::class.java)
            intent.putExtra(VideoConstant.KEY_URL, onLineUrl)
            startActivity(intent)
        }

        btn_ijk_local.setOnClickListener {
            releaseFileBlock {
                val intent = Intent(this, IJKPlayerActivity::class.java)
                intent.putExtra(VideoConstant.KEY_URL, localFile.path)
                startActivity(intent)
            }
        }

        btn_ijk_online.setOnClickListener {
            val intent = Intent(this, IJKPlayerActivity::class.java)
            intent.putExtra(VideoConstant.KEY_URL, onLineUrl)
            startActivity(intent)
        }
    }

    private fun releaseFileBlock(block: () -> Unit) {
        if (!localFile.exists()) {
            val inputStream = this.assets.open("video/video_test.mp4")
            val outStream = localFile.outputStream()
            inputStream.copyTo(outStream)
            inputStream.close()
            outStream.close()
        }
        block.invoke()
    }
}