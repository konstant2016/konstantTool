package com.konstant.tool.lite.view;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.jzvd.JzvdStd;

public class KonstantVideoPlayer extends JzvdStd {

    private boolean listViewStatus = false;
    private OnClickListener mOnBackClickListener;

    public interface OnShowListView {
        void showListView(boolean show);
    }

    private OnShowListView mShowListView;

    public KonstantVideoPlayer(Context context) {
        super(context);
    }

    public KonstantVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        bottomProgressBar.setVisibility(INVISIBLE);
    }

    public void setOnShowListView(OnShowListView showListView) {
        this.mShowListView = showListView;
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
     * 点击了返回按钮
     *
     * @return
     */
    protected void clickBack() {
        if (mOnBackClickListener != null) mOnBackClickListener.onClick(null);
    }

    public void onClickUiToggle() {//这是事件
        super.onClickUiToggle();
        Log.i(TAG, "onTouch onClickUiToggle actionUp [" + this.hashCode() + "] ");
        listViewStatus = !listViewStatus;
        if (listViewStatus){
            topContainer.setVisibility(VISIBLE);
        }else {
            topContainer.setVisibility(INVISIBLE);
        }
        if (mShowListView != null) {
            mShowListView.showListView(listViewStatus);
        }
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

    public void updateStartImage() {
        startButton.setVisibility(INVISIBLE);
    }

    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int posterImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(INVISIBLE);
        batteryTimeLayout.setVisibility(INVISIBLE);
        bottomContainer.setVisibility(INVISIBLE);
        startButton.setVisibility(INVISIBLE);
        loadingProgressBar.setVisibility(loadingPro);
        posterImageView.setVisibility(posterImg);
        bottomProgressBar.setVisibility(INVISIBLE);
        mRetryLayout.setVisibility(retryLayout);
    }
}
