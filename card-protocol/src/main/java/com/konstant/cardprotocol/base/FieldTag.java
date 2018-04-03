package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.util.ByteUtils;

/**
 * 描述:属性列表
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午6:54
 * 备注:
 */

public enum  FieldTag {
    TYPE((byte) 0x01),
    IMSI((byte) 0x02),
    ICCID((byte) 0x03),
    PLMNWACT((byte) 0x04),
    FPLMN((byte) 0x05),
    APN((byte) 0x06),
    SPN((byte) 0x07),
    R((byte) 0x08),
    C((byte) 0x09),
    KI_OPC((byte) 0x0A),
    ACC((byte) 0x0B),
    UIMID((byte) 0x0C),
    IMSI_M((byte) 0x0D),
    ACC_C((byte) 0x0E),
    SIDNID((byte) 0x0F),
    HRPDUPP((byte) 0x10),
    SIPUPP((byte) 0x11),
    MIPUPP((byte) 0x12),
    CSIM_DATA((byte) 0x13),
    AKEY((byte) 0x80),
    HRPDSS((byte) 0x81),
    SIPSS((byte) 0x82),
    MN_HASS((byte) 0x83),
    MN_AAASS((byte) 0x84),
    CHIP_ID((byte) 0x20),
    COUNTRY((byte) 0xC0),
    KI((byte) 0x70),
    OPC((byte) 0x71),
    RESULT((byte) 0xE5),//执行结果
    COS_VERSION((byte) 0xE0),
    DEVICE_INFO((byte) 0xE1),
    SESSION_KEY_DOWN((byte) 0xE2),
    PROTOCOL_VERSION((byte) 0xE4),
    DATA_KEY_DOWN((byte) 0xE3),
    SESSION_KEY_UP((byte) 0x2E),
    DATA_KEY_UP((byte) 0x3E),
    DESC((byte) 0xC1),
    CARD_STATE((byte) 0xC2),
    APDU((byte) 0xCF),
    APDU_RESPONSE((byte) 0x15),
    CARD_LIST((byte) 0xE7),
    CARD_INFO((byte) 0xE6),
    IMEI((byte) 0x21),
    MAC((byte) 0x22),
    SERIAL_NO((byte) 0x23);


    FieldTag(byte value)

    {
        this.value = value;
    }

    private byte value;

    public byte getByte() {
        return value;
    }

    public static FieldTag fromBytes(byte b) {
        for (FieldTag ft : FieldTag.values()) {
            if (ft.getByte() == b) {
                return ft;
            }
        }

        throw new IllegalArgumentException("Unknown field " + ByteUtils.printHexSingleByte(b));
    }
}
