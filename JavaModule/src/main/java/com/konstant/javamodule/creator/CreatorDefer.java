package com.konstant.javamodule.creator;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * defer：延迟创建分发器
 * 只有真正调用了subscribe时，才会对数据进行分发处理
 */

public class CreatorDefer implements CreatorInterface {

    String s = "订阅前";

    @Override
    public Observable<String> getObservable() {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just(s);
            }
        });
        s = "订阅了";
        return observable;
    }
}
