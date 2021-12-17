package com.konstant.javamodule.creator;

import org.jetbrains.annotations.NotNull;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * rangeLong ： 与range类似，上限为long，范围更大
 */

public class CreatorRangeLong implements CreatorInterface {

    @Override
    public Observable<String> getObservable() {
        /**
         * 参数参考 @OperatorRange 的使用
         */
        return Observable.rangeLong(3, 10)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NotNull Long aLong) throws Exception {
                        return "";
                    }
                });
    }
}
