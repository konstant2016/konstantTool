package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * interval：每隔一段时间发送一次事件
 */

public class CreatorInterval implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        /**
         * 参数 initialDelay ：发送第一个事件前延迟的时间(这里设置为2秒)
         * 参数 period：发送第一个事件后每隔几秒发送一次(这里设置为1秒)
         */
        return Observable.interval(2, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NotNull Long aLong) throws Exception {
                        return "第" + aLong + "次分发";
                    }
                });
    }
}
