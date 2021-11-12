package com.konstant.tool.lite.network.config;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 日志打印拦截器
 * 用于打印请求中的各种信息
 * 为避免信息暴露，此拦截器仅在debug环境下生效
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        if (!shouldIntercept(url)){
            return response;
        }
        makeLog("请求链接：" + url);
        String method = request.method();
        makeLog("请求方法：" + method);
        String requestHeaders = request.headers().toString();
        makeLog("请求头部：\n" + requestHeaders);
        RequestBody body = request.body();
        if (body != null) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            String string = buffer.readString(Charset.forName("UTF-8"));
            makeLog("请求体：\n" + string);
        }
        String responseHeaders = response.headers().toString();
        makeLog("返回头部：\n" + responseHeaders);
        MediaType mediaType = response.body().contentType();
        byte[] bytes = response.body().bytes();
        makeLog("返回内容：\n状态码：" + response.code() + "，\n返回内容：" + getResponseContent(mediaType, bytes));
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, bytes))
                .build();
    }

    private String getResponseContent(MediaType mediaType, byte[] bytes) throws IOException {
        if (mediaType == null) {
            return "类型未知，无法打印具体内容";
        }
        String type = mediaType.type();
        if (TextUtils.isEmpty(type)) {
            return "类型未知，无法打印具体内容";
        }
        if (bytes.length > 1024 * 200) {
            return "内容量太大，打印出来会导致崩溃";
        }
        if (type.contains("application") || type.contains("text")) {
            return new String(bytes, "UTF-8");
        }
        return "返回的不是JSON，具体类型为：" + mediaType.type();
    }

    private void makeLog(String msg) {
        String TAG = "OK-HTTP";
        longLog(TAG, msg + "\n");
    }

    private static void longLog(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.d(tag, "内容为空");
            return;
        }
        int segmentation = 3 * 1024;
        if (msg.length() < segmentation) {
            Log.d(tag, msg);
            return;
        }
        // 内容过长时分段打印
        while (msg.length() > segmentation) {
            String substring = msg.substring(0, segmentation);
            msg = msg.replace(substring, "");
            Log.d(tag, substring);
        }
        Log.d(tag, msg);
    }

    private boolean shouldIntercept(String url) {
        return !TextUtils.equals("https://dl.360safe.com/", url);
    }

}
