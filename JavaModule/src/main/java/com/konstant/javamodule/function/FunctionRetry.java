package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * retry(Predicate)         设置重试条件，出错时根据条件来判断是否需要重试
 * retry(Int)               设置重试次数，出错时自动重试
 * retry(Int，Predicate)     设置重试次数和重试条件，只有有一个不满足则取消重试
 * <p>
 * <p>
 * 注意：retry(3) 出错时，会重新分发出错前的所有信息,如果不再报错，则会继续往下分发
 * retryTimes()执行结果如下：
 * onNext:第一条数据
 * onNext:第二条数据
 * onNext:第一条数据
 * onNext:第二条数据
 * onNext:第三条信息
 */

public class FunctionRetry implements FunctionInterface {

    private int count = 0;

    @Override
    public void run() {
        retryTimes();
    }

    private void retryTimes() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条数据");
                emitter.onNext("第二条数据");
                count++;
                if (count < 2) {
                    emitter.onError(new Exception("发一个报错信息"));
                }
                emitter.onNext("第三条信息");
            }
        }).retry(3).subscribe(new Observer<String>() {
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


    private void retryPredicate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条数据");
                emitter.onNext("第二条数据");
                emitter.onError(new Exception("发一个报错信息"));
                emitter.onNext("第三条信息");
            }
        }).retry(new Predicate<Throwable>() {
            @Override
            public boolean test(@NotNull Throwable throwable) throws Exception {
                return false;
            }
        }).subscribe(new Observer<String>() {
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

    private void retryTimesPredicate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条数据");
                emitter.onNext("第二条数据");
                count++;
                if (count < 2) {
                    emitter.onError(new Exception("发一个保存信息"));
                }
                emitter.onNext("第三条信息");
            }
        }).retry(3, new Predicate<Throwable>() {
            @Override
            public boolean test(@NotNull Throwable throwable) throws Exception {
                return false;
            }
        }).subscribe(new Observer<String>() {
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
