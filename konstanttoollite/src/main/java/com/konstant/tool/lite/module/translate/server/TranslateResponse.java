package com.konstant.tool.lite.module.translate.server;

import java.util.List;

/**
 * Created by konstant on 2018/1/13.
 */

public class TranslateResponse {

    /**
     * from : en
     * to : zh
     * trans_result : [{"src":"apple","dst":"苹果"}]
     */

    private String from;
    private String to;
    private List<Data> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Data> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<Data> trans_result) {
        this.trans_result = trans_result;
    }

    public static class Data {
        /**
         * src : apple
         * dst : 苹果
         */

        private String src;
        private String dst;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }
}
