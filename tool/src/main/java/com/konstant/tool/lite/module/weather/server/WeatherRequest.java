package com.konstant.tool.lite.module.weather.server;

public class WeatherRequest{
    private String code;

    public WeatherRequest(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":\"")
                .append(code).append('\"');
        sb.append('}');
        return sb.toString();
    }
}