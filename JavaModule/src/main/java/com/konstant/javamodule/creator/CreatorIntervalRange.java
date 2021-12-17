package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * intervalRange : 每隔一段时间就发送一条事件，但是可以指定发送的时间范围
 * 对 interval 的扩充
 */

public class CreatorIntervalRange implements CreatorInterface {
    @Override
    public Observable<String> getObservable() {
        /**
         *  参数 start ： 从第几个开始分发
         *  参数 count ： 一共分发几条数据
         *  参数 initialDelay ： 分发前的延迟时间
         *  参数 period ： 开始分发后，下一次分发的间隔时间
         */
        return Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NotNull Long aLong) throws Exception {
                        return "第" + aLong + "条";
                    }
                });
    }
}
