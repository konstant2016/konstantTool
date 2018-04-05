package com.konstant.toollite.server.request;

/**
 * Created by konstant on 2018/4/5.
 */

public class ExpressRequest {
    private String type;
    private String postid;

    public ExpressRequest(String type, String postid) {
        this.type = type;
        this.postid = postid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"postid\":\"")
                .append(postid).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
