package com.konstant.tool.lite.view;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import cn.jzvd.JzvdStd;

public class KonstantVideoPlayer extends JzvdStd {

    private OnClickListener mOnClickListener;
    private OnClickListener mOnBackClickListener;

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

    public void setOnBackClickListener(@Nullable OnClickListener listener) {
        mOnBackClickListener = listener;
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
            });
        }
    }

    /**
     * 播放出错的时候展示这个界面
     */
    public void changeUiToError() {
        setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
        updateStartImage();
    }


    /**
     * 正在播放的时候，点击屏幕，显示这个
     */
    public void changeUiToPlayingShow() {
        setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);

    }

    /**
     * 点击了返回按钮
     *
     * @return
     */
    protected void clickBack() {
        if (mOnBackClickListener != null) mOnBackClickListener.onClick(null);
    }

    /**
     * 正在loading的时候显示这个
     */
    public void changeUIToPreparingPlaying() {
        setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
    }

    public void onClickUiToggle() {//这是事件
        super.onClickUiToggle();
        if (mOnClickListener != null) mOnClickListener.onClick(null);
    }

    /**
     * 滑动手势结束后
     */
    protected void touchActionUp() {
        Log.i(TAG, "onTouch surfaceContainer actionUp [" + this.hashCode() + "] ");
        mTouchingProgressBar = false;
        dismissVolumeDialog();
        dismissBrightnessDialog();
        startProgressTimer();
    }

    /**
     * 空实现进度滑动，因为直播不需要滑动进度
     */
    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {

    }
}
