package com.konstant.javamodule.function;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * delay ： 延迟指定时间后再执行事件的分发
 * 注意和timer，defer 的区分，
 * timer和defer是延迟创建分发器
 * delay是延迟分发事件
 */

public class FunctionDelay implements FunctionInterface {

    @Override
    public void run() {
        Disposable disposable = Observable.just("发送的消息")
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
