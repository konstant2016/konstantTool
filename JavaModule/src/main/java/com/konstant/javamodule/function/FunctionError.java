package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 出错时的操作符
 */

public class FunctionError implements FunctionInterface {

    @Override
    public void run() {

    }


    public void retry() {

    }


    /**
     * 类似于retry（Predicate predicate）
     * 区别在于，返回true则不再重试
     */
    public void retryUntil() {
        Disposable disposable = Observable.just("发送一个消息出来")
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        return System.currentTimeMillis() > 1000L;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    /**
     * 根据异常类型来判断是否需要重试
     * TODO:这里没搞明白，看这里：https://www.jianshu.com/p/b0c3669affdb
     */
    int count = 0;
    public void retryWhen() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条");
                emitter.onNext("第二条");
                emitter.onError(new IllegalArgumentException("这里发送一个异常出来"));
                emitter.onNext("第四条");
                emitter.onComplete();
            }
        })
                /**
                 * 出错时会走到这里，根据异常信息来做处理，如果这里再抛出Observable.error，那么就不会再次尝试了
                 * 如果这里走了其它Observable，则会继续反复重试
                 */
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NotNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NotNull Throwable throwable) throws Exception {
                                count ++ ;
                                if (count > 3){
                                    return Observable.error(new Exception("发生了其它错误，抛出去，不再重试"));
                                }else {
                                    return Observable.just("这个消息收不到的，只是用来让它继续重试");
                                }
                            }
                        });
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
