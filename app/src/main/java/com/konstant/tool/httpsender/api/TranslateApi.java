package com.konstant.tool.httpsender.api;

import com.konstant.tool.httpsender.response.TranslateResponse;
import com.konstant.tool.httpsender.sender.Requester;

public interface TranslateApi {

    @Requester.Method(host = "http://api.fanyi.baidu.com/", path = "api/trans/vip/translate/", response = TranslateResponse.class)
    void getTranslate(@Requester.Parameter("q") String originMsg,
                      @Requester.Parameter("from") String originType,
                      @Requester.Parameter("to") String resultType,
                      @Requester.Parameter("appid") String appid,
                      @Requester.Parameter("salt") String salt,
                      @Requester.Parameter("sign") String sign,
                      Requester.Callback<?> callback);

}
