package com.konstant.tool.lite.module.voice

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.TextUtils
import android.widget.SeekBar
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_roll_text.btn_create
import kotlinx.android.synthetic.main.activity_roll_text.et_input
import kotlinx.android.synthetic.main.activity_voice_speech.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * 描述：语音合成
 * 创建者：菜籽
 * 时间：2020/7/10:9:23 PM
 */

class VoiceSpeechActivity : BaseActivity() {

    private var mSpeech: TextToSpeech? = null
    private val mTextList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_speech)
        initBaseViews()
        setTitle("语音合成")
        btn_create.setOnClickListener { onBtnPressed() }
    }

    override fun initBaseViews() {
        super.initBaseViews()
        speed_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val speed = when {
                    progress < 10 -> {
                        "0.${progress}x"
                    }
                    progress > 10 -> {
                        "${progress - 10}x"
                    }
                    else -> {
                        "1x"
                    }
                }
                tv_speed.text = "设置语速$speed"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        pitch_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val pitch = when {
                    progress < 10 -> {
                        "0.${progress}x"
                    }
                    progress > 10 -> {
                        "${progress - 10}x"
                    }
                    else -> {
                        "1x"
                    }
                }
                tv_pitch.text = "设置音调$pitch"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun onBtnPressed() {
        if (TextUtils.isEmpty(et_input.text)) {
            showToast(getString(R.string.base_input_empty_toast))
            return
        }
        showToast("正在合成中...")
        val string = et_input.text.toString()
        mSpeech?.shutdown()
        mSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.SUCCESS) {
                Toast.makeText(this, "合成失败", Toast.LENGTH_SHORT).show()
                return@OnInitListener
            }
            val result = mSpeech?.setLanguage(Locale.CHINA)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "语音包丢失或语音不支持", Toast.LENGTH_SHORT).show()
                return@OnInitListener
            }
            val speedProgress = speed_seekbar.progress
            val speed = if (speedProgress <= 10) {
                0.1f * speedProgress
            } else {
                (speedProgress - 10) * 1.0f
            }
            val pithProgress = pitch_seekbar.progress
            val pitch = if (pithProgress <= 10) {
                0.1f * pithProgress
            } else {
                (pithProgress - 10) * 1.0f
            }
            substringText(string)
            mSpeech?.setPitch(pitch)
            mSpeech?.setSpeechRate(speed)
            var currentPosition = 0
            mSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onDone(utteranceId: String?) {
                    currentPosition++
                    speakText(currentPosition)
                }

                override fun onError(utteranceId: String?) {
                }

                override fun onStart(utteranceId: String?) {
                }

            })
            speakText(currentPosition)
        })
    }

    /**
     * 每次语音最多支持4000字，在这里做裁剪，分段存到list中，
     * 监听一段语音播放完毕后，执行下一段文字的播放
     */
    private fun substringText(text: String) {
        mTextList.clear()
        val count = if (text.length % 1000 == 0) {
            text.length / 1000;
        } else {
            text.length / 1000 + 1;
        }
        var start = 0
        for (i in 0 until count) {
            val temp = if (i == count - 1) {
                text.substring(start, start + text.length % 1000)
            } else {
                text.substring(start, start + 1000)
            }
            mTextList.add(temp)
            start += 1000
        }
    }

    private fun speakText(index: Int) {
        if (index in 0 until mTextList.size) {
            mSpeech?.speak(mTextList[index], TextToSpeech.QUEUE_FLUSH, null, "$index")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSpeech?.shutdown()
    }
}