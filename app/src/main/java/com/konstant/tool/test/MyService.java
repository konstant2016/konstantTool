package com.konstant.tool.test;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 描述:Service的使用
 * 创建人:菜籽
 * 创建时间:2018/2/26 下午12:05
 * 备注:
 */

public class MyService extends Service {

    private LocalBinder mBinder = new LocalBinder();

    // 首次创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class LocalBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    // 绑定服务时才会调用，必须实现的方法,返回值为binder，用于activity等调用者调用本service提供的方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // 解除绑定时调用，用于停止任务，回收内存等
    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    /**
     * 每次通过startService调用后，都会执行此方法
     *
     * @param intent  启动时，启动组件传递过来的Intent，
     *                如Activity可利用Intent封装所需要的参数并传递给Service
     *
     * @param flags   表示启动时是否有额外数据，可选值：
     *                1：0（没有），
     *                2：START_FLAG_REDELIVERY（当Service因内存不足而被系统kill后，则会重建服务），
     *                3：START_FLAG_RETRY（当onStartCommand调用后一直没有返回值时，
     *                会尝试重新去调用onStartCommand()）
     *
     * @param startId 指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用，
     *                stopSelfResult 可以更安全地根据ID停止服务
     *
     * @return
     * 1：START_STICKY：
     * 当Service因内存不足而被系统kill后，一段时间后内存再次空闲时，
     * 系统将会尝试重新创建此Service，一旦创建成功后将回调onStartCommand方法，
     * 但其中的Intent将是null，除非有挂起的Intent，如pendingintent，
     * 这个状态下比较适用于不执行命令、但无限期运行并等待作业的媒体播放器或类似服务。
     *
     * 2：START_STICKY_COMPATIBILITY：START_STICKY的兼容版，
     * 但是不能保证onStartCommand()方法被调用，
     * 如果应用程序的targetSdkVersion 小于 2.0版本，就会返回该值，
     * 否则返回START_STICKY，同时再次启动时只会调用onCreate（），
     * 不保证能调用onStartCommand()方法
     *
     * 3：START_NOT_STICKY
     * 当Service因内存不足而被系统kill后，即使系统内存再次空闲时，系统也不会尝试重新创建此Service。
     * 除非程序中再次调用startService启动此Service，这是最安全的选项，
     * 可以避免在不必要时以及应用能够轻松重启所有未完成的作业时运行服务。
     *
     * 4：START_REDELIVER_INTENT
     * 当Service因内存不足而被系统kill后，则会重建服务，
     * 并通过传递给服务的最后一个 Intent 调用 onStartCommand()，
     * 任何挂起 Intent均依次传递。与START_STICKY不同的是，其中的传递的Intent将是非空，
     * 是最后一次调用startService中的intent。这个值适用于主动执行应该立即恢复的作业（例如下载文件）的服务。
     *
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // 服务销毁时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
