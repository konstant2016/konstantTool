package com.konstant.toollite.data;

/**
 * Created by konstant on 2018/4/7.
 */

public class LocalDirectData {

    private String cityCode;
    private String cityName;

    public LocalDirectData() {
    }

    public LocalDirectData(String cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
