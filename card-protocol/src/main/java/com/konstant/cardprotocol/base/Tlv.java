package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.thirdtool.Preconditions;
import com.konstant.cardprotocol.thirdtool.Strings;
import com.konstant.cardprotocol.util.ByteUtils;

/**
 * Created by Jamc on 3/14/17.
 * <p>
 * TLV = Tag, Length, Value
 * Tag: 标签，对于这个属性的意义
 * Length： 长度，只用于表示数值Value的长度，并非整个TLV的长度
 * Value：数值，对于当前这个TLV对象，其存储的数值
 */

public class Tlv {

    private FieldTag tag;
    private byte[] value;

    public Tlv() {
    }

    public Tlv(FieldTag tag, byte[] value) {
        Preconditions.checkNotNull(tag);
        Preconditions.checkNotNull(value);

        this.tag = tag;
        this.value = value;
    }

    public FieldTag getTag() {
        return tag;
    }

    public void setTag(FieldTag tag) {
        this.tag = tag;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    // 返回value的长度
    public int getLength() {
        return value.length;
    }

    // 返回整体的长度(卡列表的数据量比较大，需要三个字符)
    public int getTotalLength() {
        if (tag == FieldTag.CARD_LIST) {
            return value.length + 3;
        } else {
            return value.length + 2;
        }
    }

    public String printTag() {
        return ByteUtils.printHexSingleByte(tag.getByte()).toUpperCase();
    }

    public String printLength() {
        return Strings.padStart(Integer.toHexString(getLength()), 2, '0').toUpperCase();
    }

    public String printValue() {
        return ByteUtils.printHexBinary(value).toUpperCase();
    }

    public String getPrint() {
        return printTag() + printLength() + printValue();
    }

    // 如果是卡列表，则长度+1
    public byte[] getBytes() {
        if (this.tag == FieldTag.CARD_LIST) {
            byte[] bytes = new byte[getTotalLength() + 1];
            bytes[0] = tag.getByte();                                   // tag的值，放入 T
            byte[] len = ByteUtils.shortToByte2((short) getLength());   // 数据的长度
            System.arraycopy(len, 0, bytes, 1, len.length);         // 放入 L
            System.arraycopy(value, 0, bytes, 3, getLength());          // 放入 V
            return bytes;
        } else {
            byte[] bytes = new byte[getTotalLength()];
            bytes[0] = tag.getByte();
            bytes[1] = ByteUtils.intToByte(getLength());
            System.arraycopy(value, 0, bytes, 2, getLength());
            return bytes;
        }
    }

    // 检测是否为空
    public boolean isValid() {
        return tag != null && value != null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tlv{");
        sb.append("tag='").append(printTag()).append('\'');
        sb.append(", length='").append(printLength()).append('\'');
        sb.append(", value='").append(printValue()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
