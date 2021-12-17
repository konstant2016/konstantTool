package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * onErrorResumeNext ： 执行到出错时，会中断原来分发流，并返回一个新的分发流出来，注意和onExceptionResumeNext的区别
 *                      返回新分发流的类型要求与原分发流保持一致或者是原分发流的子类
 *
 * 代码执行结果如下：
 * 第一条
 * 第二条
 * 这里收到了错误
 * 错误替换第一条
 * 错误替换第二条
 */

public class FunctionOnErrorResumeNext implements FunctionInterface{
    @Override
    public void run() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条");
                emitter.onNext("第二条");
                emitter.onError(new Throwable("发送一个错误出来")); // 这里的exception和Throwable都可以扔
                // 下面这两行是走不到的，会在上面一行挂掉后，就走到onErrorResumeNext了
                emitter.onNext("第三条");
                emitter.onComplete();
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> apply(@NotNull Throwable throwable) throws Exception {
                System.out.println("这里收到了错误");
                Observable.just("这里插入一条试试看");   // 插入的这一条是无法回调出去的
                return Observable.just("错误替换第一条","错误替换第二条");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }
}
