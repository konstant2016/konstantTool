package com.konstant.tool.httpsender.sender;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：konstant
 * 时间：2019/12/6 15:09
 * 描述：方法处理
 */

public class MethodHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HashMap<String, String> parameterMap = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();

        Requester.Method requestMethod = method.getAnnotation(Requester.Method.class);
        Requester.Headers requestHeaders = method.getAnnotation(Requester.Headers.class);
        Annotation[][] requestParameters = method.getParameterAnnotations();
        Requester.Callback callback = null;
        String requestPath = "";
        String requestHost = requestMethod.host();
        String requestMethodType = requestMethod.methodType();
        Class response = requestMethod.response();

        for (String header : requestHeaders.value()) {
            String[] split = header.split(":");
            if (split.length > 0) {
                headers.put(split[0], split[1]);
            }
        }

        for (int index = 0; index < args.length; index++) {
            String value = String.valueOf(args[index]);
            Annotation annotation = requestParameters[index][0];
            if (annotation instanceof Requester.Header) {
                String key = ((Requester.Header) annotation).value();
                headers.put(key, value);
            }
            if (annotation instanceof Requester.Parameter) {
                Requester.Parameter parameter = (Requester.Parameter) annotation;
                String key = parameter.value();
                parameterMap.put(key, value);
            }
            if (annotation instanceof Requester.Map) {
                HashMap<String, String> map = (HashMap<String, String>) args[index];
                parameterMap.putAll(map);
            }
            if (annotation instanceof Requester.Path) {
                Requester.Path parameter = (Requester.Path) annotation;
                String path = parameter.value();
                requestPath = requestMethod.path().replace("{" + path + "}", value);
            }
            if (args[index] instanceof Requester.Callback) {
                callback = (Requester.Callback) args[index];
            }
        }
        String requestUrl = requestHost + requestPath;
        sendNetworkRequest(requestUrl, requestMethodType, headers, parameterMap, response, callback);
        return null;
    }


    private void sendNetworkRequest(String url, String methodType, Map<String, String> headers, Map<String, String> parameterMap, Class responseClass, Requester.Callback callback) {
        Request.Builder builder = new Request.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        switch (methodType) {
            case Requester.MethodType.GET:
                String string = getParametersWithGet(parameterMap);
                builder.url(url + string);
                break;
            case Requester.MethodType.POST:
                builder.url(url).post(getRequestBody(parameterMap));
                break;
            case Requester.MethodType.PUT:
                builder.url(url).put(getRequestBody(parameterMap));
                break;
            case Requester.MethodType.DELETE:
                builder.url(url).delete(getRequestBody(parameterMap));
                break;
        }

        OkHttpClientBuilder.getClient().newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String string = response.body().string();
                    Object object = JSON.parseObject(string, responseClass);
                    callback.onSuccess(object);
                    return;
                }
                callback.onError(new Exception(response.message()));
            }
        });
    }

    private String getParametersWithGet(Map<String, String> parameterMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return builder.toString().replaceFirst("&", "?");
    }

    private RequestBody getRequestBody(Map<String, String> parameterMap) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
