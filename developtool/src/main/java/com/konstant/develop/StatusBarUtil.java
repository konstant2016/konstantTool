package com.konstant.develop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author liuxiaodong
 * @date 2019/4/3
 * @description
 */
public class StatusBarUtil {

    /**
     * 全透状态栏
     */
    public static void setStatusBarFullTransparent(Window window) {
        if (window == null) return;
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 状态栏透明的情况下改变状态栏字体图标黑色
     *
     * @param activity w
     */
    public static void setFullScreenStatusBarLightMode(Activity activity) {
        if (activity == null) return;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        setStatusBarFullTransparent(activity.getWindow());
        int flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    /**
     * 状态栏透明的情况下改变状态栏字体图标白色
     *
     * @param activity w
     */
    public static void setFullScreenStatusBarDarkMode(Activity activity) {
        if (activity == null || activity.getWindow() == null) return;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        setStatusBarFullTransparent(activity.getWindow());
        int flag = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    /**
     * 是否显示状态栏
     *
     * @param show true 表示显示
     */
    public static void showStatusBar(Activity activity, boolean show) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (window == null) return;
        if (show) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context c
     * @return px
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = (int) (context.getResources().getDisplayMetrics().density * 25);
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * @param activity
     * @param color      状态栏背景颜色
     * @param lightIcon  状态栏图标是否为白色（只有白色黑色两种）
     * @param showShadow 是否显示状态栏下面的那条线（阴影）
     */
    public static void setStatusBarColor(Activity activity, int color, boolean lightIcon, boolean showShadow) {
        if (activity == null) return;
        setStatusBar(activity, color, lightIcon);
        setShadow(activity, showShadow);
    }

    /**
     * 自动根据状态栏的背景色设置图标的颜色
     *
     * @param activity
     * @param color
     * @param showShadow
     */
    public static void setStatusBarColor(Activity activity, int color, boolean showShadow) {
        if (activity == null) return;
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        setStatusBar(activity, color, darkness > 0.5);
        setShadow(activity, showShadow);
    }

    private static void setStatusBar(Activity activity, int color, boolean lightIcon) {
        if (Build.VERSION.SDK_INT < 23) return;
        if (activity == null || activity.getWindow() == null) return;
        activity.getWindow().setStatusBarColor(color);
        View decorView = activity.getWindow().getDecorView();
        if (decorView == null) return;
        if (lightIcon) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private static void setShadow(Activity activity, boolean show) {
        if (show) return;
        if (!(activity instanceof AppCompatActivity)) return;
        AppCompatActivity a = (AppCompatActivity) activity;
        ActionBar actionBar = a.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setElevation(0f);
    }
}
