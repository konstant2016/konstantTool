package com.konstant.tool.lite.module.express.server;

import androidx.annotation.Keep;

import java.util.List;

/**
 * 描述:物流查询的返回体
 * 创建人:菜籽
 * 创建时间:2018/4/4 下午11:48
 * 备注:
 */

@Keep
public class ExpressResponse {

    /**
     * message : ok
     * nu : 668488305893
     * ischeck : 0
     * condition : 00
     * com : tiantian
     * status : 200
     * state : 0
     * data : [{"time":"2018-04-04 01:09:59","ftime":"2018-04-04 01:09:59","context":"快件由北京集散发往武汉分拨中心","location":""},{"time":"2018-04-04 00:35:55","ftime":"2018-04-04 00:35:55","context":"快件由北京集散发往武汉分拨中心","location":""},{"time":"2018-04-04 00:35:54","ftime":"2018-04-04 00:35:54","context":"北京集散已进行装袋扫描","location":""},{"time":"2018-04-03 23:51:42","ftime":"2018-04-03 23:51:42","context":"快件到达北京集散，上一站是无，扫描员是直营集包组","location":""},{"time":"2018-04-03 23:26:10","ftime":"2018-04-03 23:26:10","context":"北京市朝阳国贸(010-59438272 010-59438281)的尹雪贻17763519227已收件，扫描员是尹雪贻17763519227","location":""},{"time":"2018-04-03 20:50:18","ftime":"2018-04-03 20:50:18","context":"快件由北京市朝阳国贸(010-59438272 010-59438281)发往北京集散","location":""},{"time":"2018-04-03 20:50:15","ftime":"2018-04-03 20:50:15","context":"北京市朝阳国贸(010-59438272 010-59438281)的尹雪贻17763519227已收件，扫描员是尹燕铎18669750179","location":""}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private int state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-04-04 01:09:59
         * ftime : 2018-04-04 01:09:59
         * context : 快件由北京集散发往武汉分拨中心
         * location :
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
