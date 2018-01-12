package com.konstant.konstanttools.ui.activity.testactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.konstant.konstanttools.R;
import com.konstant.konstanttools.base.BaseActivity;
import com.konstant.konstanttools.views.ScaleCircleProgress;


public class ScaleCircleActivity extends BaseActivity {

    private ScaleCircleProgress mScaleCircleProgress;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_circle);
        setTitle("刻度圆进度");
        initBaseViews();
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mScaleCircleProgress = (ScaleCircleProgress) findViewById(R.id.scale_progress);
        mButton = (Button) findViewById(R.id.btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) (Math.random() * 100);
                Log.d("test", "刻度圆的值：" + i);
                mScaleCircleProgress.setProgress(i);
            }
        });
    }
}
