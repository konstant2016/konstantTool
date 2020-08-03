package com.konstant.tool.ui.activity.testactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.konstant.tool.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public class DownloadFileActivity extends AppCompatActivity {

    private static final String TAG = "DownloadFileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        findViewById(R.id.btn_start).setOnClickListener(v -> {

            // retrofit2 形式
            Download download = new Retrofit.Builder().baseUrl("").build().create(Download.class);
            download.downloadFie("下载地址").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        downloadFile(response.body(), new downloadListener() {
                            @Override
                            public void onStart() {
                                Log.d(TAG, "下载开始");
                            }

                            @Override
                            public void onDownload(long totalSize, long downloadSize) {
                                Log.d(TAG, "总大小：" + totalSize);
                                Log.d(TAG, "已下载：" + downloadSize);
                            }

                            @Override
                            public void onFinish() {
                                Log.d(TAG, "下载完毕");
                            }

                            @Override
                            public void onError() {
                                Log.d(TAG, "下载出错");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });


        // OkHttp 形式
        Request request = new Request.Builder().url("王者农药").get().build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    downloadFile(response.body(), new downloadListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onDownload(long totalSize, long downloadSize) {

                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }
        });

    }

    interface Download {
        @Streaming
        @GET
        Call<ResponseBody> downloadFie(@Url String url);
    }

    interface downloadListener {

        void onStart();

        void onDownload(long totalSize, long downloadSize);

        void onFinish();

        void onError();
    }

    private void downloadFile(ResponseBody body, downloadListener listener) {
        try {
            File file = new File("文件位置");
            byte[] bytes = new byte[1024];
            long totalSize = body.contentLength();
            long downloadSize = 0;
            InputStream inputStream = body.byteStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            int index;
            listener.onStart();
            while (true) {
                index = inputStream.read(bytes);
                if (index == -1) {
                    listener.onFinish();
                    break;
                }
                outputStream.write(bytes, 0, index);
                downloadSize += index;
                listener.onDownload(totalSize, downloadSize);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError();
        }
    }
}
