package com.konstant.tool.server.response;

/**
 * Created by konstant on 2017/12/29.
 */

public class IDNumberResponse {


    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"area":"浙江省温州市平阳县","sex":"男","birthday":"1989年03月08日"}
     */

    private int resultcode;
    private String reason;
    private Result result;

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        /**
         * area : 浙江省温州市平阳县
         * sex : 男
         * birthday : 1989年03月08日
         */

        private String area;
        private String sex;
        private String birthday;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}
