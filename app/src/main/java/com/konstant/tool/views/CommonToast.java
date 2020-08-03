package com.konstant.tool.views;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.konstant.tool.R;
import com.konstant.tool.util.SizeUtil;

import java.lang.ref.WeakReference;

/**
 * 姓名：吕卡
 * 时间：2018.12.05 20:14
 * 描述：系统级的Toast弹不出来，不晓得OS那边改了啥，所以只能这么搞了
 */

public class CommonToast {

    private static CommonToast mToast;
    private Context mContext;
    private WindowManager mManager;
    private MyHandler mHandler;
    private View mRoot;
    private LayoutParams mParams;
    private TextView mTextView;
    private static final int MSG_REMOVE_TOAST = 0x1;

    public static synchronized CommonToast getInstance(Context context) {
        if (mToast == null) {
            mToast = new CommonToast(context);
        }
        return mToast;
    }

    public void showToast(String msg) {
        showToast(msg, 3000);
    }

    public void showToast(String msg, int length) {
        if (mManager == null) return;
        removeView();
        mTextView.setText(msg);
        mManager.addView(mRoot, mParams);
        mHandler.sendEmptyMessageDelayed(MSG_REMOVE_TOAST, length);
    }

    public void cancelToast() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessage(MSG_REMOVE_TOAST);
    }

    private CommonToast(Context context) {
        mContext = context;
        mHandler = new MyHandler(this);
        initViews();
    }

    private void initViews() {
        mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mRoot = LayoutInflater.from(mContext).inflate(R.layout.common_layout_toast, null);
        SizeUtil.getInstance(mContext).resetViewWithScale(mRoot);
        mTextView = (TextView) mRoot.findViewById(R.id.toast_normal_text);
        mParams = new LayoutParams();
        int bottom = SizeUtil.getInstance(mContext).resetInt(100);
        mParams.height = LayoutParams.WRAP_CONTENT;  //高
        mParams.width = LayoutParams.WRAP_CONTENT;   //宽
        mParams.y = bottom;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = LayoutParams.TYPE_TOAST;
        mParams.flags = LayoutParams.FLAG_KEEP_SCREEN_ON
                | LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }

    private void removeView() {
        try {
            mManager.removeView(mRoot);
        } catch (Exception e) {
            // 防止没有添加view时，移除报错
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<CommonToast> reference;

        private MyHandler(CommonToast toast) {
            this.reference = new WeakReference<>(toast);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != MSG_REMOVE_TOAST) return;
            CommonToast toast = reference.get();
            if (toast == null
                    || toast.mManager == null
                    || toast.mRoot == null
                    || toast.mTextView == null)
                return;
            toast.removeView();
        }
    }
}