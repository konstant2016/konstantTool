package com.konstant.tool.ui.activity.transition

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import kotlinx.android.synthetic.main.activity_p_a_g_animation.*

class TransitionFirstActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_a_g_animation)

        btn_translate.setOnClickListener {
            val intent = Intent(this, TranslationSecondActivity::class.java)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img_view, "transition").toBundle()
            startActivity(intent, bundle)
        }

    }
}