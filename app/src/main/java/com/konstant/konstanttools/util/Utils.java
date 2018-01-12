package com.konstant.konstanttools.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    static final String LOG_TAG = "PullToRefresh";

    public static void warnDeprecation(String depreacted, String replacement) {
        Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }

    //下载JSON字符串
    public static String getData(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream inputStream = connection.getInputStream();
                int len = 0;
                byte[] bs = new byte[1024];
                while ((len = inputStream.read(bs)) != -1) {
                    baos.write(bs, 0, len);
                    baos.flush();
                }
                return baos.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取MIUI的图片列表
    public static List<String> getMIUIUrlList(String json) {
        List<String> miuiUrlList = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj1 = array.optJSONObject(i);
                String str = obj1.optString("url");
                miuiUrlList.add(str);
            }
            return miuiUrlList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getByteArray(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream inputStream = connection.getInputStream();
                int len = 0;
                byte[] bs = new byte[1024];
                while ((len = inputStream.read(bs)) != -1) {
                    baos.write(bs, 0, len);
                    baos.flush();
                }
                return baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
