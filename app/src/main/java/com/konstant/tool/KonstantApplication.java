package com.konstant.tool;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;


/**
 * Created by konstant on 2017/7/5.
 */

public class KonstantApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);   // 不同意自动接收好友添加
        EMClient.getInstance().init(getApplicationContext(), options);
        EMClient.getInstance().setDebugMode(false);
    }
}
