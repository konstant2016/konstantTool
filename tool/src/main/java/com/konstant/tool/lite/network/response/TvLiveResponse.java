package com.konstant.tool.lite.network.response;

import java.util.List;

public class TvLiveResponse {


    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {

        /**
         * channelUrl :
         * channelName :
         * channelNameEnglish :
         */

        private String channelUrl;
        private String channelName;
        private String channelNameEnglish;
        private int sort;

        public String getChannelUrl() {
            if (channelUrl == null) return "";
            return channelUrl.trim();
        }

        public void setChannelUrl(String channelUrl) {
            this.channelUrl = channelUrl;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getChannelNameEnglish() {
            return channelNameEnglish;
        }

        public void setChannelNameEnglish(String channelNameEnglish) {
            this.channelNameEnglish = channelNameEnglish;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
