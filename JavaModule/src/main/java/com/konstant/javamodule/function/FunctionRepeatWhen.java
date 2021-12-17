package com.konstant.javamodule.function;


import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 将原始 Observable 停止发送事件的标识（Complete() /  Error()）转换成1个 Object 类型数据传递给1个新被观察者（Observable）,
 * 以此决定是否重新订阅/发送原来的 Observable, 此处有2种情况 :
 * 1. 若新被观察者（Observable）返回1个Complete() /  Error()事件，则不重新订阅 & 发送原来的 Observable
 * 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
 * <p>
 * <p>
 * 其它解释：
 * Observable.empty() :发送Complete事件，但不会回调观察者的onComplete(),用来通知外部流结束循环/重复等操作
 * Observable.error() :发一个错误出去，最终会走到外部的onError()回调里面
 * Observable.just() : 在这里仅作为1个触发重新订阅被观察者的通知，发送的是具体内容外部并不关心
 */

public class FunctionRepeatWhen implements FunctionInterface {
    @Override
    public void run() {
        Disposable disposable = Observable.just("发送一条消息")
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NotNull Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NotNull Object o) throws Exception {
                                if (System.currentTimeMillis() > 100000L) {
                                    // 发送一个普通事件出去，用于通知外部流继续重复,发送的具体内容外部并不关心
                                    return Observable.just("");
                                } else {
                                    // 发送一个空的事件出去，用于通知外部流不再重复发送
                                    return Observable.empty();
                                    // return Observable.error(new Exception("这种形式也会终止重复发送的流程"));
                                }
                            }
                        });
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
