package com.konstant.tool.lite.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.konstant.tool.lite.R;

import cn.jzvd.JzvdStd;

public class KonstantVideoPlayer extends JzvdStd {

    private OnClickListener mOnClickListener;

    public KonstantVideoPlayer(Context context) {
        super(context);
    }

    public KonstantVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener listener) {
        mOnClickListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.surface_container && event.getAction() == MotionEvent.ACTION_UP) {
            if (mOnClickListener != null) mOnClickListener.onClick(v);
        }
        return true;
    }

    @Override
    public void changeUiToError() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.GONE, View.GONE, View.GONE,
                        View.GONE, View.GONE, View.GONE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }
    }
}
