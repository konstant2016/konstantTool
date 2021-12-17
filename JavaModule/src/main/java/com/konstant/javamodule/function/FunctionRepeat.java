package com.konstant.javamodule.function;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * repeat : 重复发送
 */

public class FunctionRepeat implements FunctionInterface{
    @Override
    public void run() {
        Disposable disposable = Observable.just("发送一条消息")
                .repeat(3)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
