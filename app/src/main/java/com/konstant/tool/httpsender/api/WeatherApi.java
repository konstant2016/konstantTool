package com.konstant.tool.httpsender.api;

import com.konstant.tool.httpsender.response.WeatherResponse;
import com.konstant.tool.httpsender.sender.Requester;

public interface WeatherApi {

    @Requester.Method(host = "http://tqapi.mobile.360.cn/", path = "v4/{path}.json", cache = true, methodType = Requester.MethodType.GET, response = WeatherResponse.class)
    void getWeatherInfo(@Requester.Path("path") String cityCode, Requester.Callback<?> callback);

}
