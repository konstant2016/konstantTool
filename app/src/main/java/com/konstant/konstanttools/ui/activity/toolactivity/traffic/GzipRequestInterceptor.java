package com.konstant.konstanttools.ui.activity.toolactivity.traffic;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by konstant on 2018/3/5.
 */

public class GzipRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.body() == null || request.header("Content-Encoding") != null) {
            return chain.proceed(request);
        }
        Request build = request.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(request.method(), gzip(request.body()))
                .build();
        return chain.proceed(build);
    }


    private RequestBody gzip(RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() throws IOException {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink buffer = Okio.buffer(new GzipSink(sink));
                body.writeTo(buffer);
                buffer.close();
            }
        };
    }
}
