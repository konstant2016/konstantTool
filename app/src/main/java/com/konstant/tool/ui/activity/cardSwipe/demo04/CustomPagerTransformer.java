package com.konstant.tool.ui.activity.cardSwipe.demo04;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * 实现ViewPager左右滑动时的时差
 * Created by xmuSistone on 2016/9/18.
 */
public class CustomPagerTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private ViewPager viewPager;

    public CustomPagerTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 180);
    }

    @Override
    public void transformPage(View view, float position) {
        if (viewPager == null && view.getParent() != null && view.getParent() instanceof ViewPager) {
            viewPager = (ViewPager) view.getParent();
        }
        if (viewPager == null) return;
        if (position == 0f) return;

        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.18f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
    }

    /**
     * dp和像素转换
     */
    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

}
