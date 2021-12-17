package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * timer ： 创建Observable，并在执行前延迟指定时间
 */

public class CreatorTimer implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        return Observable.timer(2, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NotNull Long aLong) throws Exception {
                        return "延迟了" + aLong + "秒";
                    }
                });
    }
}
