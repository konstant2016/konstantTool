package com.konstant.tool.test;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 描述:前台服务
 * 创建人:菜籽
 * 创建时间:2018/2/27 下午6:15
 * 备注:
 */

public class ForegroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     *
     * @param intent    保存传递过来的参数
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    // 创建通知栏的通知
    public void createNotification(){
        // 创建状态栏通知的构造器
        Notification.Builder builder = new Notification.Builder(this);
        // 为构造器设置小图标
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        // 为构造器设置大图标
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
//        // 禁止删除
//        builder.setAutoCancel(false);
//        // 右上角显示时间
//        builder.setShowWhen(true);
        // 设置通知栏标题
//        builder.setContentTitle("菜籽工具箱");
        // 创建通知
        Notification notification = builder.build();
        // 设置为前台通知，int：代表唯一标示通知的整形数；
        startForeground(1,notification);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1,notification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 关闭前台通知，参数为true表示同时移除状态栏的通知
        stopForeground(true);
    }
}
