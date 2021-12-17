package com.konstant.javamodule.function;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程调度，用于切换线程
 */

public class FunctionSchedulers implements FunctionInterface{
    @Override
    public void run() {
        Disposable disposable = Observable.just("发送的消息")
                .subscribeOn(Schedulers.io())               // 在IO线程中执行
                .subscribeOn(Schedulers.newThread())        // 开启新线程执行
                .subscribeOn(Schedulers.computation())      // CPU计算操作线程（适用于大量计算操作）
                .observeOn(Schedulers.single())             // 回调到指定线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
