package com.konstant.tool.lite.server.request;

/**
 * 描述:获取电影列表的请求体
 * 创建人:菜籽
 * 创建时间:2018/4/10 下午7:50
 * 备注:
 */


public class MovieListRequest {

    private int page;
    private int size = 10;
    private int videoExits = 1;

    public MovieListRequest(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getVideoExits() {
        return videoExits;
    }

    public void setVideoExits(int videoExits) {
        this.videoExits = videoExits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"page\":")
                .append(page);
        sb.append(",\"size\":")
                .append(size);
        sb.append(",\"videoExits\":")
                .append(videoExits);
        sb.append('}');
        return sb.toString();
    }
}
