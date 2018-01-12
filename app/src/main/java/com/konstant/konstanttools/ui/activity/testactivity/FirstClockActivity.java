package com.konstant.konstanttools.ui.activity.testactivity;

import android.os.Bundle;

import com.konstant.konstanttools.R;
import com.konstant.konstanttools.base.BaseActivity;

public class FirstClockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_clock);
        setTitle("第一种时钟");
        initBaseViews();
    }
}
