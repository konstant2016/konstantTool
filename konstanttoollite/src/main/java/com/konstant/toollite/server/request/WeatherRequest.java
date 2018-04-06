package com.konstant.toollite.server.request;

public class WeatherRequest{
    private String location;
    private String key;

    public WeatherRequest() {
    }

    public WeatherRequest(String location, String key) {
        this.location = location;
        this.key = key;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"location\":\"")
                .append(location).append('\"');
        sb.append(",\"key\":\"")
                .append(key).append('\"');
        sb.append('}');
        return sb.toString();
    }
}