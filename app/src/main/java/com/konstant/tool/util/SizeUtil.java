package com.konstant.tool.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

import java.math.BigDecimal;

public class SizeUtil {
    private static final String TAG = "SizeUtil";
    private final float UIDesign_W = 1920f;
    private final float UIDesign_H = 1080f;
    private DisplayMetrics displayMetrics;
    private static SizeUtil mInstance;
    public float mDisplayDensity;

    public static SizeUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SizeUtil(context.getApplicationContext());
        }
        return mInstance;
    }

    public float getDisplayRatio() {
        return displayMetrics.widthPixels / (displayMetrics.heightPixels + 0.0f);
    }

    public boolean ratio_8_5() {
        if (Math.abs(((float) sysWidth / sysHeight) - 1.6) < 0.02) {
            return true;
        }
        return false;
    }

    private enum ScreenOrientation {
        Portrait, Landscape
    }

    private ScreenOrientation screenOrientation = ScreenOrientation.Landscape;
    public float screenWidthScale;
    public float screenHeightScale;
    public int sysWidth, sysHeight;

    private SizeUtil(Context context) {
        init(context);
    }

    private void init(Context context) {
        displayMetrics = context.getResources().getDisplayMetrics();
        mDisplayDensity = displayMetrics.density;
        initScreenScale(displayMetrics);
    }

    // Compute the scaling ratio
    private void initScreenScale(DisplayMetrics displayMetrics) {
        sysWidth = displayMetrics.widthPixels;
        sysHeight = displayMetrics.heightPixels;
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            screenOrientation = ScreenOrientation.Landscape;
            screenWidthScale = displayMetrics.widthPixels / UIDesign_W;
            screenHeightScale = displayMetrics.heightPixels / UIDesign_H;
        } else {
            screenOrientation = ScreenOrientation.Portrait;
            screenWidthScale = displayMetrics.widthPixels / UIDesign_H;
            screenHeightScale = displayMetrics.heightPixels / UIDesign_W;
        }
    }

    public int resetInt(int value) {
        return (int) (value * screenWidthScale);
    }

    public int resetIntHeight(int value) {
        return (int) (value * screenHeightScale);
    }

    public float resetFloat(float value) {
        return value * screenWidthScale;
    }

    // Scale the given view, its contents, and all of its children
    public void resetViewWithScale(View v) {
        if (v == null || screenWidthScale == 1f) {
            return;
        }
        resetView(v);
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                resetViewWithScale(viewGroup.getChildAt(i));
            }
        }
    }

    public int convertFloatToInt(float sourceNum) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        if (sourceNum > 0 && sourceNum <= 1) {
            return 1;
        } else {
            return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
    }

    // Compute the proper scaled size, return minimal size is 1
    private int getProperScaledSize(int viewSize) {
        return viewSize * screenWidthScale > 0 && viewSize * screenWidthScale < 0.5 ? 1 : convertFloatToInt(viewSize * screenWidthScale);
    }

    // Scale view to fit, along with itself, its margins, padding, etc.
    private void resetView(View view) {
        //text size scale
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            float newTextSize = tv.getTextSize() * screenWidthScale;
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
        }

        // Scale the view itself, width and height
        // Retrieve the view's layout information
        LayoutParams layoutparams = view.getLayoutParams();
        if (layoutparams != null) {
            if (layoutparams.width > 0) {
                layoutparams.width = convertFloatToInt(screenWidthScale * layoutparams.width);
            }
            if (layoutparams.height > 0) {
                layoutparams.height = convertFloatToInt(screenWidthScale * layoutparams.height);
            }

//        // If this view has margins, scale those too
            if (layoutparams instanceof ViewGroup.MarginLayoutParams) {
                MarginLayoutParams mParams = (MarginLayoutParams) layoutparams;
                mParams.leftMargin = getProperScaledSize(mParams.leftMargin);
                mParams.rightMargin = getProperScaledSize(mParams.rightMargin);
                mParams.topMargin = getProperScaledSize(mParams.topMargin);
                mParams.bottomMargin = getProperScaledSize(mParams.bottomMargin);
            }

            // Set the layout information back into the view
            view.setLayoutParams(layoutparams);
        }

        /**
         * 有个BUG，根目录下的padding获取不到
         **/
//        // Scale the view's padding
        view.setPadding(getProperScaledSize(view.getPaddingLeft()), getProperScaledSize(view.getPaddingTop()), getProperScaledSize(view
                .getPaddingRight()), getProperScaledSize(view.getPaddingBottom()));
        view.setMinimumWidth(getProperScaledSize(view.getMinimumWidth()));
        view.setMinimumHeight(getProperScaledSize(view.getMinimumHeight()));
    }
}