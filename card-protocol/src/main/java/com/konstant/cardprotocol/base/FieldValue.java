package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.util.ByteUtils;

public class FieldValue {

    /**
     * 电信物联网广州
     */
    public static final String CARRIER_CT_M2M_GZ = "CARRIER_CT_M2M_GZ";
    //电信物联网青岛
    public static final String CARRIER_CT_M2M_QD = "CARRIER_CT_M2M_QD";
    //电信物联网上海
    public static final String INTERNATIONAL_CARRIER_MB = "INTERNATIONAL_CARRIER_MB";
    public static final String CARRIER_263 = "CARRIER_263";
    public static final String CARRIER_CU_M2M = "CARRIER_CU_M2M";
    public static final String CARRIER_CM_M2M_GD = "CARRIER_CM_M2M_GD";
    public static final String CARRIER_DUMMY = "CARRIER_DUMMY";
    public static final String CARRIER_CT_M2M_SH = "CARRIER_CT_M2M_SH";
    public static final String CARRIER_CM_M2M_GX = "CARRIER_CM_M2M_GX";
    public static final String CARRIER_HUAHONG = "CARRIER_HUAHONG";
    public static final String CARRIER_CM_M2M_GX_SZ = "CARRIER_CM_M2M_GX_SZ";
    public static final String CARRIER_TOPTOLINK = "CARRIER_TOPTOLINK";
    public static final String CARRIER_CM_VSIM_GX = "CARRIER_CM_VSIM_GX";


    public static final byte[] TYPE_CHINA_MOBILE = new byte[]{0x00};
    public static final byte[] TYPE_CHINA_TELECOM = new byte[]{0x01};
    public static final byte[] TYPE_CHINA_UNICOM = new byte[]{0x02};
    public static final byte[] TYPE_CHINA_UNICOM_263 = new byte[]{0x03};
    public static final byte[] TYPE_CHINA_MB = new byte[]{0x04};
    public static final byte[] TYPE_END = new byte[]{0x05};

    //执行结果-成功
    public static final byte[] EXECUTE_RESULT_SUCCESS = ByteUtils.parseHexBinary("9000");//2个字节
    public static final byte[] SIM_NOT_EXISTS_RESULT = ByteUtils.parseHexBinary("6A82");//2个字节

    //SIM状态
    public static final byte[] SIM_STATUS_INACTIVE = new byte[]{0x00};//未激活
    public static final byte[] SIM_STATUS_ACTIVE = new byte[]{0x55};//激活启用
    public static final byte[] SIM_STATUS_DELETED = new byte[]{(byte) 0xAA};//删除 一个字节

    //虚卡/实卡切换
    public static final byte[] ACTUAL_TO_VISUAL = new byte[]{(byte) 0xAA};//实卡切换到虚卡 一个字节
    public static final byte[] VISUAL_TO_ACTUAL = new byte[]{0x55};//虚卡切换到实卡

    public static byte[] carrierCodeToType(String code) {
        switch (code) {
            case CARRIER_CM_M2M_GD:
            case CARRIER_CM_M2M_GX:
            case CARRIER_HUAHONG:
            case CARRIER_CM_M2M_GX_SZ:
            case CARRIER_CM_VSIM_GX:
                return TYPE_CHINA_MOBILE;
            case CARRIER_CT_M2M_GZ:
            case CARRIER_CT_M2M_QD:
            case CARRIER_CT_M2M_SH:
                return TYPE_CHINA_TELECOM;
            case INTERNATIONAL_CARRIER_MB:
                return TYPE_CHINA_MB;
            case CARRIER_263:
                return TYPE_CHINA_UNICOM_263;
            case CARRIER_CU_M2M:
            case CARRIER_TOPTOLINK:
                return TYPE_CHINA_UNICOM;
            //如果都没有，就是暂时不支持
            default:
                return TYPE_END;
        }
    }

}
