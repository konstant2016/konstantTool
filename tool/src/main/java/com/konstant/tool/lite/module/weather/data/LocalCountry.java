package com.konstant.tool.lite.module.weather.data;

/**
 * Created by konstant on 2018/4/7.
 */

public class LocalCountry {

    private String directCode;
    private String directName;

    public LocalCountry() {
    }

    public LocalCountry(String directCode, String directName) {
        this.directCode = directCode;
        this.directName = directName;
    }

    public String getDirectCode() {
        return directCode;
    }

    public void setDirectCode(String directCode) {
        this.directCode = directCode;
    }

    public String getDirectName() {
        return directName;
    }

    public void setDirectName(String directName) {
        this.directName = directName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocalCountry)) return false;
        LocalCountry that = (LocalCountry) obj;
        return this.directCode.equals(that.directCode) && this.directName.equals(that.directName);
    }



}
