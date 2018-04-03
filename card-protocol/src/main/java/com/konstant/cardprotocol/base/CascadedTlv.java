package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.thirdtool.Optional;

import java.util.EnumMap;

public class CascadedTlv extends Tlv {

    private EnumMap<FieldTag, Tlv> fields = new EnumMap<>(FieldTag.class);

    private CascadedTlv(FieldTag tag, byte[] value) {
        super(tag, value);
    }

    public CascadedTlv(Tlv tlv) {
        super(tlv.getTag(), tlv.getValue());
        byte[] data = tlv.getValue();
        int offset = 0;
        Byte zero = 0x00;

        while (offset < data.length) {
            byte tag = data[offset];
            if (zero.equals(tag)) {
                return;
            }
            FieldTag fieldTag = FieldTag.fromBytes(tag);
            byte len = data[offset + 1];
            byte[] tmp = new byte[len];
            System.arraycopy(data, offset + 2, tmp, 0, len);

            Tlv t = new Tlv(fieldTag, tmp);
            fields.put(fieldTag, t);
            offset += 2 + len;

        }

    }

    public Optional<Tlv> getField(FieldTag tag) {
        return Optional.fromNullable(fields.get(tag));
    }

}
