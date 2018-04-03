package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.base.Tlv;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;
import com.konstant.cardprotocol.thirdtool.Optional;
import com.konstant.cardprotocol.thirdtool.Preconditions;
import com.konstant.cardprotocol.util.ByteUtils;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述:加密的指令体
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午2:03
 * 备注:
 */

public class Command {

    protected int commandLength = CommandHeader.HEADER_LENGTH;
    protected CommandHeader header = new CommandHeader();
    protected EnumMap<FieldTag, Tlv> fields = new EnumMap<>(FieldTag.class);

    // 检查所有条件是否都已满足
    public boolean isSatisfied() {
        ImmutableSet<FieldTag> requiredFields = getRequiredFields();
        Set<FieldTag> field = new HashSet<>(fields.keySet());
        field.retainAll(requiredFields);

        if (field.size() != requiredFields.size()) {
            return false;
        }
        return true;
    }

    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of();
    }

    public CommandHeader getCommandHeader() {
        return header;
    }

    // 添加一个tlv
    public void addField(Tlv tlv) {
        Preconditions.checkArgument(tlv.isValid(), "tlv {" + tlv.printTag() + "} is invalid");
        fields.put(tlv.getTag(), tlv);
        commandLength += tlv.getTotalLength();
        short length = new Integer(commandLength - CommandHeader.HEADER_LENGTH).shortValue();
        header.setBodyLength(length);
    }

    // 设置tlv
    public void setField(Tlv tlv) {
        Preconditions.checkArgument(tlv.isValid(), "tlv {" + tlv.printTag() + "} is invalid");
        fields.put(tlv.getTag(), tlv);
    }

    // 根据tag返回TLV
    public Optional<Tlv> getField(FieldTag tag) {
        if (tag == null) {
            return Optional.absent();
        }
        Tlv tlv = fields.get(tag);
        return Optional.fromNullable(tlv);
    }

    // 把指令转为byte数组发出去
    public final byte[] getBytes() {
        Preconditions.checkArgument(isSatisfied(), "command {" + header.getType().name() + "} is invalid");
        byte[] bytes;

        if (fields.containsKey(FieldTag.CARD_LIST)) {
            bytes = new byte[commandLength + 1];
        } else {
            bytes = new byte[commandLength];
        }

        // 把header的长度保存到字节数组中
        System.arraycopy(header.getBytes(), 0, bytes, 0, CommandHeader.HEADER_LENGTH);

        int offset = CommandHeader.HEADER_LENGTH;
        for (FieldTag tag : fields.keySet()) {
            byte[] tmp = fields.get(tag).getBytes();
            System.arraycopy(tag, 0, bytes, offset, tmp.length);
            offset += tmp.length;
        }
        return bytes;
    }

    // 通过byte数组转换为command指令
    public void fromBytes(byte[] bytes) {
        header = new CommandHeader();
        header.fromBytes(bytes);

        //des 加密如果不是8的倍数会补全0,所以解密之后需要去掉
        int allLength = header.getBodyLength() + CommandHeader.HEADER_LENGTH;
        byte[] data = new byte[allLength];

        if (bytes.length >= allLength) {
            System.arraycopy(bytes, 0, data, 0, allLength);
        }

        commandLength = CommandHeader.HEADER_LENGTH;
        fields.clear();

        int offset = CommandHeader.HEADER_LENGTH;
        while (offset < data.length) {
            byte tag = data[offset];
            FieldTag fieldTag = FieldTag.fromBytes(tag);

            if (fieldTag == FieldTag.CARD_LIST) {
                byte[] lenByte = new byte[2];
                System.arraycopy(data, offset+1, lenByte, 0, 2);
                short len = ByteUtils.byteToShort(lenByte);
                byte[] tmp = new byte[len];
                System.arraycopy(data, offset + 3, tmp, 0, len);
                Tlv tlv = new Tlv(fieldTag, tmp);
                this.addField(tlv);
                offset += tlv.getTotalLength()+1;
            } else {
                byte len;
                len = data[offset + 1];
                byte[] tmp = new byte[len];
                System.arraycopy(data, offset + 2, tmp, 0, len);
                Tlv tlv = new Tlv(fieldTag, tmp);
                this.addField(tlv);
                offset += tlv.getTotalLength();
            }
        }
    }

    public CommandType getType(){
        Preconditions.checkNotNull(header);
        return header.getType();
    }

    public String getHexCommand(){
        return ByteUtils.printHexBinary(getBytes());
    }

}
