package com.konstant.tool.lite.module.weather.data;

/**
 * Created by konstant on 2018/4/7.
 */

public class LocalCountry {

    private String cityCode;
    private String cityName;

    public LocalCountry() {
    }

    public LocalCountry(String cityCode, String cityName) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof LocalCountry)) return false;
        LocalCountry that = (LocalCountry) obj;
        return this.cityCode.equals(that.cityCode) && this.cityName.equals(that.cityName);
    }



}
