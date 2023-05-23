package com.konstant.develop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxJava {

    public static void main() {
        System.out.println("111111");
        Observable<Integer> no1 = Observable.just(1);
        Observable<String> no2 = Observable.just("2");
        Observable<Integer> no3 = Observable.just(3).delay(5, TimeUnit.SECONDS);
        Observable<Integer> no4 = Observable.just(4);

        Observable[] array = new Observable[4];
        array[0] = no1;
        array[1] = no2;
        array[2] = no3;
        array[3] = no4;

        Disposable subscribe = Observable.zipArray(new Function<Object[], List<Object>>() {
                    @Override
                    public List<Object> apply(Object[] objects) throws Exception {
                        System.out.println("222222");
                        return new ArrayList<>(Arrays.asList(objects));
                    }
                }, false, array.length, array)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        boolean is = o instanceof List;
                        if (is) {
                            List<Object> s = (List<Object>) o;
                            for (int i = 0; i < s.size(); i++) {
                                System.out.println(""+ i + "==" + s.get(i));
                            }
                        }
                        System.out.println("333333");
                        System.out.println(is);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("444444");
                        System.out.println(throwable.getStackTrace().toString());
                    }
                });
    }

}
