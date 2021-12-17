package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * onErrorReturn ： 分发器出错时会走到onErrorReturn内部，内部处理完后返回正确的结果，
 * 要求返回的结果类型与被订阅者发送的数据类型保持一致
 */

public class FunctionOnErrorReturn implements FunctionInterface {
    @Override
    public void run() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条");
                emitter.onNext("第二条");
                emitter.onNext("第三条");
                emitter.onComplete();
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(@NotNull Throwable throwable) throws Exception {
                return "出错了，返回这里的结果继续往下走";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }
}
