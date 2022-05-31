package com.konstant.develop

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ViewFlipper
import io.reactivex.disposables.CompositeDisposable

class SearchView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {

    private val words = listOf<String>("AAAAAA", "BBBBBBBBB", "CCCCCCCCCC", "DDDDDDDDDDDDD")
    private val text by lazy { findViewById<TextView>(R.id.tv_search_word) }

    private val startAnimation by lazy {
        TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 1000
        }
    }

    private val stopAnimation by lazy {
        TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, -1f
        ).apply {
            duration = 1000
            startOffset = 1000
        }
    }

    private val dispose = CompositeDisposable()

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_chapter_search_view, null)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(view, params)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateWords()
    }

    private fun updateWords() {
        val s = findViewById<ViewFlipper>(R.id.tv_search_word)
        s.setInAnimation(context,R.anim.bottom_in)
        s.setOutAnimation(context,R.anim.top_out)
        s.flipInterval = 1000

        words.forEach {
            val textView = TextView(context)
            textView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
            textView.text = it
            textView.setTextColor(Color.BLACK)
            textView.textSize = 15f
            s.addView(textView)
        }
        s.startFlipping()
        handler.postDelayed({
            updateWords()
        }, 3000)
    }

    override fun onDetachedFromWindow() {
        dispose.dispose()
        super.onDetachedFromWindow()

    }
}