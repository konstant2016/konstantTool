package com.konstant.tool.ui.activity.testactivity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.konstant.tool.R;
import com.konstant.tool.base.BaseActivity;
import com.konstant.tool.views.CircleProgress;


public class CircleActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener{

    CircleProgress mCircleProgress;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        setTitle("圆环进度条");
        initBaseViews();
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mCircleProgress = (CircleProgress) findViewById(R.id.mProgress);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCircleProgress.setValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
