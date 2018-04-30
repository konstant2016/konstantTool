package com.konstant.tool.lite.data;

/**
 * Created by konstant on 2018/4/7.
 */

public class LocalCountryData {

    private String cityCode;
    private String cityName;

    public LocalCountryData() {
    }

    public LocalCountryData(String cityCode, String cityName) {
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
        if (obj == null || !(obj instanceof LocalCountryData)) {
            return false;
        }
        if (this == obj){
            return true;
        }
        LocalCountryData data = (LocalCountryData) obj;
        return data.cityCode.equals(cityCode) && data.cityName.equals(cityName);
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = result * 17 + (cityCode == null ? 0 : cityCode.hashCode());
        result = result * 17 + (cityName == null ? 0 : cityName.hashCode());
        return result;
    }
}
