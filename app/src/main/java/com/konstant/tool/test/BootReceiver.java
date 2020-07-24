package com.konstant.tool.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.konstant.tool.ui.activity.MainActivity;

/**
 * Created by konstant on 2018/2/28.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentStart = new Intent(context, MainActivity.class);
        intentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentStart);

        Intent intentService = new Intent(context, ForegroundService.class);
        context.startService(intentService);
    }
}
