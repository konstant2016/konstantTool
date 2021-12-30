package com.konstant.javamodule.filter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 根据具体类型进行筛选
 */

public class FilterOfType implements FilterInterface {
    @Override
    public void run() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("第一条");
                emitter.onNext(5);
                emitter.onNext("第二条");
                emitter.onNext(12);
                emitter.onComplete();
            }
        }).ofType(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }
}
