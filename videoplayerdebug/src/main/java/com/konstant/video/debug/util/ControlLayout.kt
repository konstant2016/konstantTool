package com.konstant.video.debug.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.konstant.video.debug.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.player_control_view.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class ControlLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface ControlListener {
        fun onPlayStateChanged(playing: Boolean)
        fun onBackPressed()
        fun onSeekBarChanged(total: Int, position: Int)
        fun getVideoTotal(): Long
        fun getVideoPosition(): Long
    }

    private var listener: ControlListener? = null
    private val isPlayed = AtomicBoolean(false)
    private val interceptProgress = AtomicBoolean(false)
    private val dispose = CompositeDisposable()

    init {
        LayoutInflater.from(context).inflate(R.layout.player_control_view, this)
        initVisible()
        initListener()
        updateControlView()
    }

    fun setControlListener(listener: ControlListener) {
        this.listener = listener
    }

    override fun setOnClickListener(l: OnClickListener?) {

    }

    private fun initVisible() {
        root_layout.setOnClickListener {
            if (layout_back.visibility == View.VISIBLE) {
                layout_back.visibility = View.GONE
                layout_seek.visibility = View.GONE
                player_status.visibility = View.GONE
            } else {
                layout_back.visibility = View.VISIBLE
                layout_seek.visibility = View.VISIBLE
                player_status.visibility = View.VISIBLE
            }
        }
    }

    private fun initListener() {
        layout_back.setOnClickListener {
            listener?.onBackPressed()
        }
        player_status.setOnClickListener {
            isPlayed.set(!isPlayed.get())
            if (isPlayed.get()) {
                player_status.setImageResource(R.drawable.player_ic_played)
            } else {
                player_status.setImageResource(R.drawable.player_ic_pause)
            }
            listener?.onPlayStateChanged(isPlayed.get())
        }
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (interceptProgress.get()) return
                listener?.onSeekBarChanged(100, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.dispose.clear()
    }

    private fun updateControlView() {
        val dispose = Observable.interval(1000, 1000, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener?.let { listener ->
                    val total = listener.getVideoTotal()
                    val position = listener.getVideoPosition()
                    tv_position.text = getTimeString(position)
                    tv_total.text = getTimeString(total)
                    val progress = (position.toFloat() / total * 100).toInt()
                    interceptProgress.set(true)
                    seek_bar.progress = progress
                    interceptProgress.set(false)
                }
            }
        this.dispose.add(dispose)
    }

    private fun getTimeString(time: Long):String {
        val pos = if (time > 0) time else 0
        val totalSeconds = (pos / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds) else String.format(Locale.US, "%02d:%02d", minutes, seconds)
    }

}