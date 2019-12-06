package com.konstant.tool.httpsender;

import com.konstant.tool.httpsender.api.TranslateApi;
import com.konstant.tool.httpsender.api.WeatherApi;
import com.konstant.tool.httpsender.response.TranslateResponse;
import com.konstant.tool.httpsender.response.WeatherResponse;
import com.konstant.tool.httpsender.sender.Requester;
import com.konstant.tool.httpsender.sender.SenderManager;

public class NetworkHelper {

    private static NetworkHelper mHelper;

    public static NetworkHelper getInstance() {
        if (mHelper == null) {
            synchronized (NetworkHelper.class) {
                if (mHelper == null) {
                    mHelper = new NetworkHelper();
                }
            }
        }
        return mHelper;
    }

    public void getWeatherInfo(String cityCode, Requester.Callback<WeatherResponse> callback) {
        SenderManager.getInstance().getApi(WeatherApi.class).getWeatherInfo(cityCode, callback);
    }

    public void getTranslate(String originMsg, String originType, String resultType, String appid, String salt, String sign, Requester.Callback<TranslateResponse> callback) {
        SenderManager.getInstance().getApi(TranslateApi.class).getTranslate(originMsg, originType, resultType, appid, salt, sign, callback);
    }
}
