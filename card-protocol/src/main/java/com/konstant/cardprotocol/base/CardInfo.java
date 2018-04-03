package com.konstant.cardprotocol.base;

/**
 * 描述:卡信息
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午6:53
 * 备注:
 */

public class CardInfo {

    private String iccid;
    private String country;
    private String desc;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
