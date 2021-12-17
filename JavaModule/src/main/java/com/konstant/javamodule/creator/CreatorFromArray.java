package com.konstant.javamodule.creator;

import io.reactivex.Observable;

/**
 * FromArray 操作符
 * 创建后，会根据传入的array以此发送给订阅者
 * 无需再调用 emitter.onComplete()
 */

public class CreatorFromArray implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        String[] stringArray = {"第一条", "第二条", "第三条", "第四条"};
        return Observable.fromArray(stringArray);
    }
}
