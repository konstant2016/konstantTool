package com.konstant.tool.ui.activity.testactivity;

import android.os.Bundle;

import com.konstant.tool.R;
import com.konstant.tool.base.BaseActivity;

public class SecondClockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_clock);
        setTitle("第二种时钟");
        initBaseViews();
    }
}
