package com.konstant.konstanttools.ui.activity.testactivity;

import android.os.Bundle;

import com.konstant.konstanttools.R;
import com.konstant.konstanttools.base.BaseActivity;

public class SecondClockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_clock);
        setTitle("第二种时钟");
        initBaseViews();
    }
}
