package com.konstant.konstanttools.test;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 描述:通过message的方式实现,服务端的进程
 * 创建人:菜籽
 * 创建时间:2018/2/27 上午10:54
 * 备注:
 */

public class MessageService extends Service {

    final Messenger mMessenger = new Messenger(new MessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class MessageHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                // 根据message的type进行区分
            }

            // 给客户端回复消息
            Messenger messenger = msg.replyTo;
            Message message = Message.obtain(null, 0);
            Bundle bundle = new Bundle();
            bundle.putString("","");
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
