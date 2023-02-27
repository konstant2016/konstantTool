package com.konstant.video.debug.exo

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SeekParameters
import com.google.android.exoplayer2.offline.StreamKey
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.FileDataSource

class ExoVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : StyledPlayerView(context, attrs, defStyleAttr) {

    private val cacheStreamKeys = arrayListOf(
        StreamKey(HlsMasterPlaylist.GROUP_INDEX_VARIANT, 1),
        StreamKey(HlsMasterPlaylist.GROUP_INDEX_AUDIO, 1),
        StreamKey(HlsMasterPlaylist.GROUP_INDEX_SUBTITLE, 1),
        StreamKey(3, 1),
        StreamKey(4, 1)
    )

    private val exoPlayer: ExoPlayer

    init {
        val bandwidthMeter = DefaultBandwidthMeter.getSingletonInstance(context.applicationContext)
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(context.applicationContext, videoTrackSelectionFactory)
        val builder = ExoPlayer.Builder(context.applicationContext)
        builder.setBandwidthMeter(bandwidthMeter)
        builder.setTrackSelector(trackSelector)
        builder.setLoadControl(DefaultLoadControl())
        builder.setSeekParameters(SeekParameters.CLOSEST_SYNC)
        exoPlayer = builder.build()
        this.player = exoPlayer
        this.player?.playWhenReady = true
        this.useController = false
    }

    /**
     * 播放指定链接
     */
    fun playUrl(url: String) {
        val mediaSource = when {
            Uri.parse(url).lastPathSegment?.endsWith("m3u") == true
                    || Uri.parse(url).lastPathSegment?.endsWith("m3u8") == true
            -> buildHLSMediaSource(url)
            url.startsWith("http") -> buildHttpMediaSource(url)
            else -> buildLocalMediaSource(url)
        }
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    /**
     * 播放本地文件时的媒体解析器
     */
    private fun buildLocalMediaSource(url: String): MediaSource {
        val sourceFactory = FileDataSource.Factory()
        val factory = ProgressiveMediaSource.Factory(sourceFactory)
        val item = MediaItem.fromUri(url)
        return factory.createMediaSource(item)
    }

    /**
     * 播放HLS视频
     */
    private fun buildHLSMediaSource(url: String): MediaSource {
        val sourceFactory = DefaultHttpDataSource.Factory()
        val item = MediaItem.Builder()
            .setUri(url)
            .build()
        return HlsMediaSource.Factory(sourceFactory).createMediaSource(item)
    }

    /**
     * 播放非HLS的在线视频时的媒体解析器
     */
    private fun buildHttpMediaSource(url: String): MediaSource {
        val sourceFactory = DefaultHttpDataSource.Factory()
        val factory = ProgressiveMediaSource.Factory(sourceFactory)
        val item = MediaItem.fromUri(url)
        return factory.createMediaSource(item)
    }
}