package com.konstant.cardprotocol.info;

import com.konstant.cardprotocol.thirdtool.Preconditions;
import com.konstant.cardprotocol.util.ByteUtils;

/**
 * 描述:明文头
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午4:13
 * 备注:
 */

public class InfoHeader {

    public static int LENGTH = 3;

    private short messageId;
    private Type type;

    public InfoHeader() {
    }

    public InfoHeader(short messageId, Type type) {
        this.messageId = messageId;
        this.type = type;
    }

    public short getMessageId() {
        return messageId;
    }

    public void setMessageId(short messageId) {
        this.messageId = messageId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    // 0，1 位放的是messageId ， 2 位放的是type类型
    public byte[] getBytes() {
        byte[] bytes = new byte[3];
        byte[] shortByte = ByteUtils.shortToByte2(messageId);
        System.arraycopy(shortByte, 0, bytes, 0, 2);
        bytes[2] = type.getByte();
        return bytes;
    }

    // 根据byte数据形成infoHeader
    public void fromBytes(byte[] bytes) {
        // 检查参数是否满足条件
        Preconditions.checkArgument(bytes.length >= 3);
        // 读取messageId
        byte[] msgId = new byte[2];
        System.arraycopy(bytes, 0, msgId, 0, 2);
        messageId = ByteUtils.byteToShort(msgId);
        type = Type.fromByte(bytes[2]);
    }
}
