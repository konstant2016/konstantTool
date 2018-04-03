package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.util.ByteUtils;

/**
 * 描述:指令类型
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午6:57
 * 备注:
 */

public enum CommandType {

    INSERT((byte) 0xAC), //写卡
    DELETE((byte) 0xDC), //删除
    SWITCH((byte) 0x5A),//切换卡
    SIM_ID((byte) 0xCD),//获取卡片/设备ID
    LIST((byte) 0xEC), //查询卡列表
    RESET((byte) 0x50), //重新启动SIM协议栈
    INIT((byte) 0x51), //初始化紫米固件运行环境
    SEEDINIT((byte) 0x52), //商米Seed IMSI初始化


    TRAVERSE((byte) 0xCF), //透传

    RESULT((byte) 0xC5),//通用操作的回复
    LIST_RESPONSE((byte) 0x10),//获取卡列表的回复
    SIM_ID_RESPONSE((byte) 0x12),//获取卡片/设备ID回复
    TRAVERSE_RESPONSE((byte) 0x15); //透传回复


    CommandType(byte value) {
        this.value = value;
    }

    private byte value;

    public byte getByte() {
        return value;
    }

    public static CommandType fromByte(byte b) {
        for (CommandType ft : CommandType.values()) {
            if (ft.getByte() == b) {
                return ft;
            }
        }

        throw new IllegalArgumentException("Unknown command type " + ByteUtils.printHexSingleByte(b));
    }

}
