package com.konstant.tool.lite.module.express.server;

import android.support.annotation.Keep;
import android.text.TextUtils;

import java.util.List;

@Keep
public class ExpressData {

    private String name;
    private String company;
    private String phoneNo;
    private String status;
    private String number;
    private List<Message> messages;

    public ExpressData() {
    }

    public ExpressData(String company, String phoneNo, String status, String number, List<Message> messages) {
        this.company = company;
        this.phoneNo = phoneNo;
        this.status = status;
        this.number = number;
        this.messages = messages;
    }

    public ExpressData(String company, String number, String status, String name) {
        this.company = company;
        this.number = number;
        this.status = status;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private String context;
        private String time;

        public Message(String context, String time) {
            this.context = context;
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public String getTime() {
            return time;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressData that = (ExpressData) o;
        return TextUtils.equals(number, that.number);
    }

}
