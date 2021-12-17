package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * range ： 连续分发一个事件序列，可以指定分发范围，从指定位置开始，不能设置间隔时间
 */

public class CreatorRange implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {

        /**
         * 参数 start ：从start开始分发（这里设置为3，表示从3开始分发，分发的第一个值就是3）
         * 参数 count ：一共分发的条数（这里设置为10，表示一共分发10条，那么最后一条就是 3+10-1 = 13）
         */
        return Observable.range(3, 10)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NotNull Integer integer) throws Exception {
                        return "第" + integer + "条";
                    }
                });
    }
}
