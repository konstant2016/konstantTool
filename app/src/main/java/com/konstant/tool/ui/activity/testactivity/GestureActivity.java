package com.konstant.tool.ui.activity.testactivity;

import android.os.Bundle;

import com.konstant.tool.R;
import com.konstant.tool.base.BaseActivity;
import com.konstant.tool.views.KonstantTextview;

/**
 * Created by konstant on 2017/11/30.
 */

public class GestureActivity extends BaseActivity {

    private KonstantTextview mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        setTitle("手势缩放");
        mTextView = (KonstantTextview) findViewById(R.id.text);
        initBaseViews();

    }


}
