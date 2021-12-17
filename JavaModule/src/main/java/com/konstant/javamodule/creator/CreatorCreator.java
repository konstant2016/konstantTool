package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * create 操作符
 * 用来创建操作流的最基本形式
 */

public class CreatorCreator implements CreatorInterface {

    @Override
    public Observable<String> getObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一条数据");
                emitter.onNext("第二条数据");
                emitter.onComplete();
            }
        });
    }

}
