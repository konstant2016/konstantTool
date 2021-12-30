package com.konstant.javamodule.filter;

import org.jetbrains.annotations.NotNull;

import javax.xml.transform.Source;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * 条件过滤
 */

public class FilterFilter implements FilterInterface {
    @Override
    public void run() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onComplete();
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NotNull Integer integer) throws Exception {
                // 只有2的倍数才能往下分发
                return integer % 2 == 0;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NotNull Integer integer) {
                System.out.println("onNext:" + integer);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
