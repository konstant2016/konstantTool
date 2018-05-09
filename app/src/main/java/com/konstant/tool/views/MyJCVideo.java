package com.konstant.tool.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by konstant on 2017/6/23.
 */

public class MyJCVideo extends JCVideoPlayerStandard {

    public MyJCVideo(Context context) {
        this(context, null);
    }

    public MyJCVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGone();
    }

    private void setGone() {

        bottomProgressBar.setVisibility(View.INVISIBLE);
    }

    public ImageView getBack(){
        backButton.setClickable(true);
        return backButton;
    }
    public ImageView getFullScreenButton(){
        fullscreenButton.setClickable(true);
        return fullscreenButton;
    }
}
