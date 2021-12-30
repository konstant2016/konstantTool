package com.konstant.javamodule.filter;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FilterSkip implements FilterInterface {
    @Override
    public void run() {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .skip(1)
                .skipLast(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept--1:" + integer);
                    }
                });

        // 发送事件特点：发送数据0-5，每隔1s发送一次，每次递增1；第1次发送延迟0s
        Disposable subscribe = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                .skip(1, TimeUnit.SECONDS)
                .skipLast(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("accept--2:" + aLong);
                    }
                });

    }
}
