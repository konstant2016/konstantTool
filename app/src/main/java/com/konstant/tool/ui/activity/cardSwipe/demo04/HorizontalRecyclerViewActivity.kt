package com.konstant.tool.ui.activity.cardSwipe.demo04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konstant.tool.R
import com.konstant.tool.ui.activity.cardSwipe.demo03.CardPageAdapter
import kotlinx.android.synthetic.main.activity_horizontal_recycler_view.*

class HorizontalRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_horizontal_recycler_view)

        view_pager.setPageTransformer(false,CustomPagerTransformer(this))
        view_pager.adapter = CardPageAdapter(supportFragmentManager)

    }

    private fun animationConvert(){

    }
}