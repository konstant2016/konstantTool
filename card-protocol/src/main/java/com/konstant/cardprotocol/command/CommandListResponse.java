package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CardInfo;
import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.base.Tlv;
import com.konstant.cardprotocol.thirdtool.Bytes;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;
import com.konstant.cardprotocol.thirdtool.Optional;
import com.konstant.cardprotocol.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class CommandListResponse extends Command {

    private static final ImmutableSet<FieldTag> requiredFields
            = ImmutableSet.of(FieldTag.RESULT, FieldTag.CARD_STATE, FieldTag.CARD_LIST);

    public CommandListResponse() {
        header.setType(CommandType.LIST_RESPONSE);
    }

    @Override
    public void addField(Tlv tlv) {
        if (tlv.getTag().equals(FieldTag.CARD_INFO)) {
            Optional<Tlv> optional = getField(FieldTag.CARD_LIST);
            byte[] exists = optional.isPresent() ? optional.get().getValue() : new byte[0];

            // 每添加一个TLV,则需要从commandLength中剔除此TLV的长度，以便于添加下一个TLV
            commandLength = optional.isPresent() ? commandLength - optional.get().getTotalLength() : commandLength;

            byte[] concated = Bytes.concat(exists, tlv.getBytes());
            tlv = new Tlv(FieldTag.CARD_LIST,concated);
        }
        super.addField(tlv);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return requiredFields;
    }

    public List<CardInfo> getCardList() {
        Optional<Tlv> optional = getField(FieldTag.CARD_LIST);
        LinkedList<CardInfo> list = new LinkedList<>();

        if (!optional.isPresent()) {
            return list;
        }

        int offset = 0;
        byte[] data = optional.get().getValue();

        while (offset < data.length) {
            byte len = data[offset + 1];
            byte[] tmp = new byte[len];
            System.arraycopy(data, offset + 2, tmp, 0, len);

            CardInfo cardInfo = tlvToCardInfo(tmp);
            list.add(cardInfo);
            offset += 2 + len;
        }

        return list;
    }

    private CardInfo tlvToCardInfo(byte[] data) {
        int offset = 0;
        CardInfo info = new CardInfo();

        while (offset < data.length) {
            FieldTag tag = FieldTag.fromBytes(data[offset]);
            byte len = data[offset + 1];
            byte[] tmp = new byte[len];
            System.arraycopy(data, offset + 2, tmp, 0, len);
            Tlv tlv = new Tlv(tag, tmp);
            setField(info, tlv);
            offset += 2 + len;
        }

        return info;
    }

    private void setField(CardInfo info, Tlv t) {
        switch (t.getTag()) {
            case ICCID:
                info.setIccid(StringUtils.flip(t.printValue()));
                break;
            case COUNTRY:
                info.setCountry(new String(t.getValue()));
                break;
            case DESC:
                info.setDesc(new String(t.getValue()));
                break;
            default: //应该不会走进来这里
        }
    }
}
