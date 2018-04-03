package com.konstant.cardprotocol.info;


/**
 * 描述:明文的数据类型
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午4:05
 * 备注:
 */


public enum Type {

    // 获取设备版本
    VERSION((byte) 0xB0),
    VERSION_RESOPNSE((byte) 0xD4),

    // 交换秘钥
    KEY_EXCHANGE((byte) 0xD4),
    KEY_EXCHANGE_RESPONSE((byte) 0xD0),

    // 错误类型
    ERRORTYPE((byte) 0xC5);

    private byte value;

    Type(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    // 根据字节转换为Type枚举
    public static Type fromByte(byte b) {
        for(Type ft : Type.values()) {
            if (ft.getByte() == b) {
                return ft;
            }
        }

        return ERRORTYPE;
        //        throw new IllegalArgumentException("Unknown command type " + ByteUtils.printHexSingleByte(b));
    }
}
