package com.konstant.tool.lite.data.bean.main;

public class ConfigData {

    /**
     * title : 翻译
     * type : 1
     */

    private String title;
    private String type;

    public ConfigData() {
    }

    public ConfigData(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
