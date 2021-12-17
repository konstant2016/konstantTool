package com.konstant.javamodule.function;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 订阅一个事件的结果
 */

public class FunctionSubscribe implements FunctionInterface {

    @Override
    public void run() {
        Disposable subscribe = Observable.just("发送的指令")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
