package com.konstant.javamodule.creator;

import io.reactivex.Observable;

/**
 * Just操作符
 * 一个just相当于 emitter.onNext() + emitter.onComplete()
 */

public class CreatorJust implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        return Observable.just("这是来自于Just发送的数据");
    }
}
