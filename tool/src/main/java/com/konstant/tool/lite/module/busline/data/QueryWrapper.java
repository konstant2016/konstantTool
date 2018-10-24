package com.konstant.tool.lite.module.busline.data;

/**
 * 时间：2018/8/3 9:36
 * 作者：吕卡
 * 描述：
 */
public class QueryWrapper {

    private String cityName;

    private String route;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public QueryWrapper(String cityName, String route) {

        this.cityName = cityName;
        this.route = route;
    }

    public QueryWrapper() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof QueryWrapper)) return false;
        QueryWrapper that = (QueryWrapper) o;
        return that.cityName.equals(cityName) && that.route.equals(route);
    }
}
