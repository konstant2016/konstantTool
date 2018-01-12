package com.konstant.konstanttools.ui.activity.testactivity;

import android.os.Bundle;

import com.konstant.konstanttools.R;
import com.konstant.konstanttools.base.BaseActivity;
import com.konstant.konstanttools.views.KonstantTextview;

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
