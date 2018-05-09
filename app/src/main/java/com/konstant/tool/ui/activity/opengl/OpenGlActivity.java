package com.konstant.tool.ui.activity.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.konstant.tool.R;
import com.konstant.tool.base.BaseActivity;

public class OpenGlActivity extends BaseActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
        if (isOpenGLSupported()) {
            mGLSurfaceView = new GLSurfaceView(this);
            mGLSurfaceView.setRenderer(new TriangleRenderer());
            setContentView(mGLSurfaceView);
        } else {
            setContentView(R.layout.activity_open_gl);
            initBaseViews();
            setTitle("OpenGL实战");
            Toast.makeText(this, "当前设备不支持OpenGL", Toast.LENGTH_SHORT).show();
        }
    }

    //判断设备是否支持OpenGL
    private boolean isOpenGLSupported() {

        // 判断虚拟机是否支持
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        // 判断真机是否支持
        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        return supportsEs2 || isEmulator;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLSurfaceView != null) mGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGLSurfaceView != null) mGLSurfaceView.onResume();
    }
}
