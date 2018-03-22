package com.konstant.konstanttools.test;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 描述:用于测试服务的同进程和不同进程之间的使用
 * 创建人:菜籽
 * 创建时间:2018/2/27 下午6:14
 * 备注:
 */

public class TestActivity extends AppCompatActivity {


    /** 在同一进程里面绑定service

     private MyService mService;

     private ServiceConnection mConnect = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
    MyService.LocalBinder binder = (MyService.LocalBinder) service;
    mService = binder.getService();

    }

    @Override public void onServiceDisconnected(ComponentName name) {
    mService = null;
    }
    };


     @Override public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
     super.onCreate(savedInstanceState, persistentState);
     Intent intent = new Intent(TestActivity.this, MyService.class);
     bindService(intent,mConnect, Service.BIND_AUTO_CREATE);
     }

     **/


    /**
     * 在不同进程中绑定service，通过messenger实现
     */


    // 用于发送消息给Service的messenger
    private Messenger mMessenger;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    // 用于接受Service返回消息的messenger
    private Messenger mMessengerReply = new Messenger(new ReceiveHandler());

    private static class ReceiveHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 用于接受到Service返回的消息
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(TestActivity.this, MessageService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    // 发送消息给Service
    protected void sendMessage() throws Exception {
        Message message = Message.obtain(null, 0, "");
        message.replyTo = mMessengerReply;
        mMessenger.send(message);
    }
}
