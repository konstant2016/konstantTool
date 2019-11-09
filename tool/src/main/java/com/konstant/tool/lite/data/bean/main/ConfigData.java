package com.konstant.tool.lite.data.bean.main;

import java.util.List;

public class ConfigData {

    private List<ConfigsBean> configs;

    public List<ConfigsBean> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigsBean> configs) {
        this.configs = configs;
    }

    public static class ConfigsBean {
        /**
         * title : 翻译
         * type : 1
         */

        private String title;
        private String type;

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
}
