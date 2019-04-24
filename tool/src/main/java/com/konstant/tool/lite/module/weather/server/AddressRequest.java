package com.konstant.tool.lite.module.weather.server;

public class AddressRequest {

    private String key;
    private String location;

    public AddressRequest(String key, double latitude, double longitude) {
        this.key = key;
        this.location = "" + latitude + "," + longitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"key\":\"")
                .append(key).append('\"');
        sb.append(",\"location\":\"")
                .append(location).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
