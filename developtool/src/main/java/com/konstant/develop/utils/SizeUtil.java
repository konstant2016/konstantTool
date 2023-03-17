package com.konstant.develop.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 时间：2019/7/26 11:57
 * 创建：菜籽
 * 描述：
 */

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 时间：2022/7/25 17:36
 * 作者：吕卡
 * 备注：屏幕缩放工具
 * 使用方法：1、在XML布局中的尺寸使用px单位
 * 2、使用 this.resetView(view)对指定view进行缩放
 * 3、使用this.resetValue(value)对指定值进行缩放
 */

public class SizeUtil {

    private float designSize = 1080f;      // 设计图中的尺寸
    private Boolean widthBase = false;     // 以宽（高）为基准
    private Boolean isDp = false;          // 传入的是dp还是px
    private float mScaleGrade = 1f;        // 缩放等级

    /**
     * 构造方法
     *
     * @param context    上下文
     * @param designSize UI稿的设计尺寸，以dp为单位
     * @param isDp       true 传入数值单位是dp，false 传入的数值单位是 px
     * @param widthBase  true 基于宽度缩放，false 基于高度缩放
     */
    public SizeUtil(Context context, int designSize, boolean isDp, boolean widthBase) {
        this.designSize = designSize;
        this.isDp = isDp;
        this.widthBase = widthBase;
        initScaleGrade(context);
    }

    public SizeUtil(Context context) {
        this(context, 1200, false, false);
    }

    public int resetValue(int value) {
        return (int) (mScaleGrade * value);
    }

    public float resetValue(float value) {
        return mScaleGrade * value;
    }

    public void resetView(View view) {
        resetScaleView(view);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                resetView(group.getChildAt(i));
            }
        }
    }

    private void initScaleGrade(Context context) {
        WindowManager service = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            service.getDefaultDisplay().getRealSize(point);
        } else {
            service.getDefaultDisplay().getSize(point);
        }

        int widthPixels = point.x;   // 获取实际的屏幕宽度
        int heightPixels = point.y;  // 获取实际的屏幕高度

        float widthDp = convertPx2Dp(context, widthPixels);
        float heightDp = convertPx2Dp(context, heightPixels);

        if (isDp) {
            if (widthBase) {
                mScaleGrade = widthDp / designSize;
            } else {
                mScaleGrade = heightDp / designSize;
            }
        } else {
            if (widthBase) {
                mScaleGrade = widthPixels / designSize;
            } else {
                mScaleGrade = heightPixels / designSize;
            }
        }
    }

    // 最小值取 1
    private int resetParam(int param) {
        int v = Math.round(mScaleGrade * param);
        return Math.max(v, 1);
    }

    /**
     * 把PX转换为DP
     */
    private float convertPx2Dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale + 0.5f);
    }

    private void resetScaleView(View view) {
        if (view == null || mScaleGrade == 1f) return;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            float size = textView.getTextSize() * mScaleGrade;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) return;
        if (params.width > 0) {
            params.width = Math.round(mScaleGrade * params.width);
        }
        if (params.height > 0) {
            params.height = Math.round(mScaleGrade * params.height);
        }
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
            marginParams.leftMargin = resetParam(marginParams.leftMargin);
            marginParams.rightMargin = resetParam(marginParams.rightMargin);
            marginParams.topMargin = resetParam(marginParams.topMargin);
            marginParams.bottomMargin = resetParam(marginParams.bottomMargin);
        }
        view.setPadding(resetParam(view.getPaddingLeft()), resetParam(view.getPaddingTop()), resetParam(view.getPaddingRight()), resetParam(view.getPaddingBottom()));
        view.setMinimumWidth(resetParam(view.getMinimumWidth()));
        view.setMinimumHeight(resetParam(view.getMinimumHeight()));
        view.setLayoutParams(params);
    }
}