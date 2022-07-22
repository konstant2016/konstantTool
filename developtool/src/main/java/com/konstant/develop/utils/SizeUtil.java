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

public class SizeUtil {

    private final float mDesignHeight = 1200f;      // 设计图中的默认高度
    private float mScaleGrade = 1f;         // 缩放等级

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

    public SizeUtil(Context context) {
        initScaleGrade(context);
    }

    private void initScaleGrade(Context context) {
        WindowManager service = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            service.getDefaultDisplay().getRealSize(point);
        } else {
            service.getDefaultDisplay().getSize(point);
        }

        int widthPixels = point.x;                           // 获取实际的屏幕宽度
        int heightPixels = point.y;                          // 获取实际的屏幕高度
        if (widthPixels > heightPixels) {                    // 横向屏幕
            mScaleGrade = heightPixels / mDesignHeight;
        } else {                                             // 纵向屏幕
            mScaleGrade = widthPixels / mDesignHeight;
        }
    }

    // 最小值取 1
    private int resetParam(int param) {
        int v = Math.round(mScaleGrade * param);
        if (v > 1) return v;
        return 1;
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