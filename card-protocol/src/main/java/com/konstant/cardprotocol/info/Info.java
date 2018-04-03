package com.konstant.cardprotocol.info;

import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.base.Tlv;
import com.konstant.cardprotocol.thirdtool.Preconditions;

import java.util.EnumMap;

/**
 * 描述:明文信息
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午4:04
 * 备注:
 */

public class Info {

    private InfoHeader infoHeader;
    private int lenght = InfoHeader.LENGTH;

    protected EnumMap<FieldTag, Tlv> fields = new EnumMap<>(FieldTag.class);

    public Info() {
    }

    public Info(InfoHeader infoHeader) {
        this.infoHeader = infoHeader;
    }

    public InfoHeader getInfoHeader() {
        return infoHeader;
    }

    public void setInfoHeader(InfoHeader infoHeader) {
        this.infoHeader = infoHeader;
    }

    // 添加TLV
    public void addField(Tlv tlv) {
        Preconditions.checkArgument(tlv.isValid(), "tlv {" + tlv.printTag() + "} is invalid");
        fields.put(tlv.getTag(), tlv);
        lenght = lenght + tlv.getTotalLength();
    }

    // 获取TLV
    public Tlv getField(FieldTag tag) {
        if (fields.containsKey(tag)) {
            return fields.get(tag);
        }
        return null;
    }

    // 获取数据
    public byte[] getBytes() {
        byte[] bytes = new byte[lenght];
        System.arraycopy(infoHeader.getBytes(), 0, bytes, 0, InfoHeader.LENGTH);
        int offset = InfoHeader.LENGTH;
        for (FieldTag it : fields.keySet()) {
            byte[] temp = fields.get(it).getBytes();
            System.arraycopy(temp, 0, bytes, offset, temp.length);
            offset += temp.length;
        }
        return bytes;
    }

    // 从byte中转换为info
    public void fromBytes(byte[] bytes) {
        lenght = InfoHeader.LENGTH;
        fields.clear();
        infoHeader = new InfoHeader();
        infoHeader.fromBytes(bytes);        // 只从byte的前几位拿取数据，用于构建header

        int offset = InfoHeader.LENGTH;
        while (offset < bytes.length) {
            byte tag = bytes[offset];       // 找到TLV中T的位置
            FieldTag fieldTag = FieldTag.fromBytes(tag);
            byte len = bytes[offset + 1];
            byte[] temp = new byte[len];
            System.arraycopy(bytes, offset + 2, temp, 0, len);

            Tlv tlv = new Tlv(fieldTag, temp);
            addField(tlv);

            offset += tlv.getTotalLength();
        }

    }

    public EnumMap<FieldTag, Tlv> getFields() {
        return fields;
    }
}
