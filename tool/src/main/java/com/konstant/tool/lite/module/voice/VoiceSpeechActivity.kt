package com.konstant.tool.lite.module.voice

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_roll_text.*
import java.util.*

/**
 * 描述：语音合成
 * 创建者：吕卡
 * 时间：2020/7/10:9:23 PM
 */

class VoiceSpeechActivity : BaseActivity() {

    private val mSpeech by lazy { TextToSpeech(this, TextToSpeech.OnInitListener { speechListener(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_speech)
        initBaseViews()
        setTitle("语音合成")
        btn_create.setOnClickListener { onBtnPressed() }
    }

    override fun initBaseViews() {
        super.initBaseViews()

    }

    private fun onBtnPressed() {
        if (TextUtils.isEmpty(et_input.text)) {
            showToast(getString(R.string.base_input_empty_toast))
            return
        }
        mSpeech.setPitch(1.0f)
        mSpeech.setSpeechRate(1.0f)
        val string = et_input.text.toString()
        showLoading(true, "正在合成中...")
        mSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun speechListener(status: Int) {
        showLoading(false, "正在合成中...")
        if (status != TextToSpeech.SUCCESS) {
            Toast.makeText(this, "合成失败", Toast.LENGTH_SHORT).show()
            return
        }
        val result = mSpeech.setLanguage(Locale.CHINA)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(this, "语音包丢失或语音不支持", Toast.LENGTH_SHORT).show()
        }
    }
}