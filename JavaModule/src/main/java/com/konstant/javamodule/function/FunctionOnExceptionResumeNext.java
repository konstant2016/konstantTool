package com.konstant.javamodule.function;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * onExceptionResumeNext：出现异常时，恢复新分发流往下发送
 * 按照例子中的写法，当执行到 emitter.onError(new Exception("发送一个错误出来"))时，后续的分发器不会再执行，
 * 转而回调到onExceptionResumeNext内部，用来接管新的分发流，在新的分发流内部往下继续发送，要求新的分发流发射的数据类型为初始分发类型的同类或子类
 * 注意：这里只能拦截Exception，Throwable是无法拦截的
 *
 * 代码执行结果如下：
 * 第一条
 * 第二条
 * 这里收到了错误，接下来发送一个新的信息出去
 * 出错后的第一条
 * 出错后的第二条
 */

public class FunctionOnExceptionResumeNext implements FunctionInterface {
    @Override
    public void run() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条");
                emitter.onNext("第二条");
                emitter.onError(new Exception("发送一个错误出来")); // 注意这里只能扔Exception，不能扔Throwable
                // 下面这两行是走不到的，会在上面一行挂掉后，就走到onExceptionResumeNext了
                emitter.onNext("第三条");
                emitter.onComplete();
            }
        }).onExceptionResumeNext(new ObservableSource<String>() {
            @Override
            public void subscribe(@NotNull Observer<? super String> observer) {
                System.out.println("这里收到了错误，接下来发送一个新的信息出去");
                observer.onNext("出错后的第一条");
                observer.onNext("出错后的第二条");
                observer.onComplete();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }
}
