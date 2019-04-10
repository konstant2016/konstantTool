package com.konstant.test;

import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RxJava {

    private static final String TAG = "RxJava";

    public void rxJava01() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable emit 1");
                emitter.onNext(1);
                Log.d(TAG, "Observable emit 2");
            }
        }).subscribe(new Observer<Integer>() {

            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                if (i == 2) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    interface Result {
        void onSuccess(Weather list);

        void onError();
    }

    // 利用RxJava进行线程切换
    public void rxJava02(final Result result) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Log.d(TAG, "subscribe," + Thread.currentThread().getName());
                Request request = new Request.Builder()
                        .url("http://tqapi.mobile.360.cn/v4/101010600.json")
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                Response response = call.execute();
                emitter.onNext(response);
            }
        })
                .map(new Function<Response, Weather>() {
                    @Override
                    public Weather apply(Response response) throws Exception {
                        Log.d(TAG, "map," + Thread.currentThread().getName());
                        if (!response.isSuccessful() || response.body() == null) {
                            return null;
                        }
                        return new Gson().fromJson(response.body().string(), Weather.class);
                    }
                })
                .doOnNext(new Consumer<Weather>() {
                    @Override
                    public void accept(Weather weather) throws Exception {
                        Log.d(TAG, "这里可以保存数据," + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather>() {
                    @Override
                    public void accept(Weather data) throws Exception {
                        Log.d(TAG, "accept data," + Thread.currentThread().getName());
                        result.onSuccess(data);
                    }
                }, throwable -> {
                    Log.d(TAG, "accept throwable," + Thread.currentThread().getName());
                    throwable.printStackTrace();
                    result.onError();
                });
    }

    // 利用concat合并结果
    public void rxJava03() {
        Observable<Weather> cache = Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> emitter) throws Exception {
                // 用缓存中获取数据
                Weather weather = new Weather();
                if (weather != null) {
                    emitter.onNext(weather);
                    emitter.onComplete();
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<Weather> network = Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> emitter) throws Exception {

            }
        });

        Disposable disposable = Observable.concat(cache, network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather>() {
                    @Override
                    public void accept(Weather weather) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    Disposable disposable;

    public void rxJava04() {
        disposable = Observable.interval(0,10, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return aLong++;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 10) {
                            disposable.dispose();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
