package com.konstant.javamodule.creator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * fromIterable:传入一个list，逐一进行发送
 * 无需再调用 emitter.onComplete()
 */

public class CreatorFromIterable implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        List<String> list = new ArrayList<>();
        list.add("第一条");
        list.add("第二条");
        list.add("第三条");
        return Observable.fromIterable(list);
    }
}
