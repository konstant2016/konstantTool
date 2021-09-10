package com.konstant.tool.ui.activity.cardSwipe;

import android.content.Context;
import android.util.TypedValue;

/**
 * 介绍：一些配置
 * 界面最多显示几个View
 * 每一级View之间的Scale差异、translationY等等
 * <p>
 * <p>
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */

public class CardConfig {

    public static int MAX_SHOW_COUNT;   // 同时最多显示几层卡片
    public static float SCALE_GAP;      // X 方向上的缩小比例，也就是第二层比最上层小0.05，也就是95%的大小
    public static int TRANS_Y_GAP;      // Y 方向上的向下偏移量，15dp

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 2;
        SCALE_GAP = 0.05f;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }
}
