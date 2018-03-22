package com.konstant.konstanttools.ui.activity.toolactivity.traffic;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by konstant on 2018/3/9.
 */

public class Upload {

    private static Upload mUpload;
    private static OkHttpClient mClient;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/octet-stream;charset=utf-8");

    public static Upload getInstance() {
        if (mUpload == null) {
            synchronized (Upload.class) {
                if (mUpload == null) {
                    mUpload = new Upload();
                    mClient = new OkHttpClient();
                }
            }
        }
        return mUpload;
    }

    public void uploadByteArray(byte[] bytes, Callback callback) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, bytes);
        Request request = new Request.Builder()
                .url("http://10.144.109.169:8080/post")
                .post(requestBody)
                .build();
        mClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onResult(false,e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    callback.onResult(true,response.body().string());
                }else {
                    callback.onResult(false,response.toString());
                }
            }
        });
    }

    public interface Callback {
        void onResult(boolean state,String data);
    }

}
