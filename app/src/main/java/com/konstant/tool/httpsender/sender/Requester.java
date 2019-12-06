package com.konstant.tool.httpsender.sender;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface Requester {

    // 在运行时生效
    @Retention(RetentionPolicy.RUNTIME)
    // 作用在方法上
    @Target(ElementType.METHOD)
    @interface Method {

        // 链接的baseUrl
        String host();

        // 链接的路径
        String path() default "";

        // 请求方法
        @MethodType String methodType() default MethodType.GET;

        // 请求返回数据解析成的对象
        Class response();

        // 是否需要缓存
        boolean cache() default false;

    }


    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @StringDef({MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE})
    @interface MethodType {
        String GET = "GET";
        String POST = "POST";
        String PUT = "PUT";
        String DELETE = "DELETE";
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Header {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Headers {
        String[] value();
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Parameter {
        String value();
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Map {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Path {
        String value();
    }

    interface Callback<T> {

        void onSuccess(T result);

        void onError(Exception exception);
    }

}
