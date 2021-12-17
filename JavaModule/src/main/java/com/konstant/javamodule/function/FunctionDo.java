package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * do 操作符，具体看下面的log信息
 */

public class FunctionDo implements FunctionInterface {

    @Override
    public void run() {
        Observable.range(0, 5)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NotNull Integer integer) throws Exception {
                        return "第" + integer + "条数据";
                    }
                })
                // 每发送一次数据都会走一次这里
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        System.out.println("doOnEach" + stringNotification.getValue());
                    }
                })
                // 执行onNext之前会调用到这里
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doOnNext:" + s);
                    }
                })
                // 执行onNext之后会调用到这里
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doAfterNext:" + s);
                    }
                })
                // 执行onComplete后会调用到这里
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnComplete");
                    }
                })
                // 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnSubscribe");
                    }
                })
                // 无论执行挂掉还是正常走完，都会走到这里
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doFinally");
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NotNull String s) {
                        System.out.println("onNext:" + s);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        System.out.println("onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}
