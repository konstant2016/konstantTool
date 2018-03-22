package com.konstant.konstanttools.test;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 描述:防止Service被杀死
 * 创建人:菜籽
 * 创建时间:2018/2/28 上午11:05
 * 备注:
 */

public class ServiceKilledByAppStop extends Service {

    private IntentFilter mFilter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 当Service首次启动时，创建一个广播接受者，当此程序被杀死时，会发送广播，自己接收到之后，再次启动起来
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Intent intentS = new Intent(ServiceKilledByAppStop.this, ServiceKilledByAppStop.class);
                    startService(intentS);
                }
            };
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // 在Service初始化的时候，注册广播接收器
        mFilter = new IntentFilter();
        mFilter.addAction("com.konstant.restart.service");
        registerReceiver(mReceiver,mFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Service被销毁时，发送广播，用于重启此service，并且解除广播接收器
        Intent intentBroad = new Intent();
        intentBroad.setAction("com.konstant.restart.service");
        sendBroadcast(intentBroad);
        unregisterReceiver(mReceiver);
    }
}
