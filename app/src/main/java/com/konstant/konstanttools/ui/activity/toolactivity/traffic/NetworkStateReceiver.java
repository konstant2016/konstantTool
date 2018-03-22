package com.konstant.konstanttools.ui.activity.toolactivity.traffic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by konstant on 2018/3/14.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    private static final int NETWORK_NONE = -1;     // 没有网络

    private static final int NETWORK_MOBILE = 0;    // 移动网络

    private static final int NETWORK_WIFI = 1;      // WIFI网络

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
        }
    }
}
