package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.thirdtool.Preconditions;
import com.konstant.cardprotocol.util.ByteUtils;

/**
 * 描述:加密指令的头部
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午2:04
 * 备注:
 */

public class CommandHeader {

    public static final int HEADER_LENGTH = 8;

    private byte version = 0x01;                // 协议版本
    private short messageId;                    // 此次消息的编号
    private byte[] reserved = new byte[]{0x00, 0x00};// 随机的填充字段
    private CommandType type;                   // 指令类型
    private short bodyLength;                   // 指令体的长度


    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public short getMessageId() {
        return messageId;
    }

    public void setMessageId(short messageId) {
        this.messageId = messageId;
    }

    public byte[] getReserved() {
        return reserved;
    }

    public void setReserved(byte[] reserved) {
        this.reserved = reserved;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public short getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(short bodyLength) {
        this.bodyLength = bodyLength;
    }

    // 将commandHeader指令转换为数组，返回给调用者
    public byte[] getBytes() {
        byte[] bytes = new byte[HEADER_LENGTH];
        bytes[0] = version;

        byte[] msgId = ByteUtils.shortToByte2(messageId);
        System.arraycopy(msgId, 0, bytes, 1, 2);        // 把messageId放到byte数组中
        System.arraycopy(reserved, 0, bytes, 3, 2);     // 把reserved放入到数组中

        bytes[5] = type.getByte();                                // header类型占用一个位置
        byte[] bdl = ByteUtils.shortToByte2(bodyLength);          // body的长度标示，占两位
        System.arraycopy(bdl, 0, bytes, 6, 2);          // 把长度标示放到byte数组中
        return bytes;
    }

    // 从byte数组中解析获取到commandHeader
    public void fromBytes(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= HEADER_LENGTH,"commandHeader的长度不对");

        byte[] tmp = new byte[2];

        // 获取到版本信息
        version = bytes[0];

        // 获取到messageId
        System.arraycopy(bytes,1,tmp,0,tmp.length);
        messageId = ByteUtils.byteToShort(tmp);

        // 获取到指令类型
        type = CommandType.fromByte(bytes[5]);

        // 获取到指令体的长度
        System.arraycopy(bytes,6,tmp,0,tmp.length);
        bodyLength = ByteUtils.byteToShort(tmp);

    }
}
