package com.konstant.tool.ui.activity.cardSwipe.demo03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konstant.tool.R
import kotlinx.android.synthetic.main.activity_view_pager_card.*

class ViewPagerCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_card)

        view_pager.offscreenPageLimit = 5

        view_pager.setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
                .addAnimationType(PageTransformerConfig.ROTATION)//默认动画 default animation rotation  旋转  当然 也可以一次性添加两个  后续会增加更多动画
                .setRotation(-45f)//旋转角度
                .addAnimationType(PageTransformerConfig.ALPHA)//默认动画 透明度 暂时还有问题
                .setViewType(PageTransformerConfig.BOTTOM)
                .setTranslationOffset(80)
                .setScaleOffset(80)
                .create(view_pager))

        val adapter = CardPageAdapter(supportFragmentManager)
        view_pager.adapter = adapter

    }
}