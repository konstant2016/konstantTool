package com.konstant.cardprotocol.capsule;

import com.konstant.cardprotocol.util.ByteUtils;

/**
 * 描述:对明文/密文，再进行一次包裹
 * 创建人:菜籽
 * 创建时间:2018/3/30 上午11:41
 * 备注:
 */

public class Capsule {

    private byte type;      //0x53 表示明文， 0xD2 表示密文
    private byte[] sessionId = new byte[4];     //终端识别码 4bytes
    private byte[] reserved = new byte[3];      //保留位，暂定填充随机数 3bytes
    private byte[] data;

    private final byte typeCommand = (byte) 0xD2;   // 密文
    private final byte typeInfo = (byte) 0x53;      // 明文


    public boolean isEncrypted() {
        return type == typeCommand;
    }

    public boolean isPlain() {
        return type == typeInfo;
    }

    // 不获取type，只获取data中的数据
    public byte[] getData() {
        return data;
    }

    // 填充指令（密文）
    public void setCommand(byte[] command) {
        this.type = typeCommand;
        this.data = command;
    }

    // 填充指令（明文）
    public void setInfo(byte[] info) {
        this.type = typeInfo;
        this.data = info;
    }

    // 获取SessionId
    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }

    // 获取完整的byte数据，生成byte数组，返回出去
    public byte[] getBytes() {
        if (sessionId == null || sessionId.length != 4) {
            throw new IllegalArgumentException("sessionId长度错误");
        }
        reserved = ByteUtils.random(3);
        byte[] bytes = new byte[8 + data.length];

        bytes[0] = type;
        System.arraycopy(sessionId, 0, bytes, 1, 4);    // 把sessionId的内容拷贝到bytes的从1开始，拷贝4位
        System.arraycopy(reserved, 0, bytes, 5, 3);     // 把reserId的内容拷贝到bytes的从5开始，拷贝3位
        System.arraycopy(data, 0, bytes, 8, data.length);   // 把data中的数据拷贝到bytes中，从8开始，全部拷贝

        return bytes;
    }
}
