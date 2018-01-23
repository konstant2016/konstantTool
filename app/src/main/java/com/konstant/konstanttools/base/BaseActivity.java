package com.konstant.konstanttools.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.konstant.konstanttools.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;
    private int mRequestCode = 12;
    private String mReason;
    private String mPermission;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 沉浸状态栏
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        // 滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }


    protected void initBaseViews() {
        findViewById(R.id.img_back).setOnClickListener((view) -> finish());
    }

    protected void setTitle(String s) {
        View view = findViewById(R.id.title_bar);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(s);
    }

    @Override
    public void setTitle(int stringId) {
        View view = findViewById(R.id.title_bar);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(getText(stringId));
    }

    protected void setSubTitle(String s) {
        View view = findViewById(R.id.title_bar);
        TextView textView = (TextView) view.findViewById(R.id.sub_title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(s);
    }

    protected void setSubTitle(int stringId) {
        View view = findViewById(R.id.title_bar);
        TextView textView = (TextView) view.findViewById(R.id.sub_title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(getText(stringId));
    }


    // 申请权限
    protected void requestPermission(String permission, String reason) {
        mReason = reason;
        mPermission = permission;
        // 判断自身是否拥有此权限
        if (PackageManager.PERMISSION_DENIED == checkSelfPermission(permission)) {
            // 如果没有，就去申请权限
            requestPermissions(new String[]{permission}, mRequestCode);
        } else {
            // 如果有，则返回给子类调用
            onPermissionResult(true);
        }
    }

    // 系统activity回调的权限申请结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            // 权限申请成功
            onPermissionResult(true);
        } else {
            // 权限申请失败，判断是否需要弹窗解释原因
            if (shouldShowRequestPermissionRationale(permissions[0])) {
                // 如果需要弹窗，则弹窗解释原因
                showReasonDialog();
            } else {
                // 否则，告诉子类权限申请失败
                onPermissionResult(false);
            }
        }
    }

    // 展示给用户说明权限申请原因
    private void showReasonDialog() {
        new AlertDialog.Builder(this)
                .setMessage(mReason)
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                    onPermissionResult(false);
                })
                .setPositiveButton("确定", ((dialog, which) -> {
                    dialog.dismiss();
                    requestPermissions(new String[]{mPermission}, mRequestCode);
                }))
                .create().show();
    }

    // 需要子类实现的权限申请结果，不写成接口是因为并不是所有的activity都需要申请权限
    protected void onPermissionResult(boolean result) {

    }

    // 隐藏软键盘
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode ==
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return;
        }
        if (getCurrentFocus() == null) {
            return;
        }
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 滚动界面，防止输入法遮挡视图
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}






