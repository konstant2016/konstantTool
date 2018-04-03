package com.konstant.cardprotocol.info;

import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.base.Tlv;

/**
 * 描述:用于生成Info的辅助工具
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午6:40
 * 备注:
 */

public class InfoHelper {

    // 生成用于查询版本号的明文指令
    public static Info buildVersionInfo(short messageId) {
        InfoHeader header = new InfoHeader();
        header.setMessageId(messageId);
        header.setType(Type.VERSION);
        Info info = new Info(header);
        return info;
    }

    // 用于生成交换session key 的明文指令
    public static Info buildSessionKeyExchangeInfo(short messageId, byte[] key) {
        InfoHeader header = new InfoHeader();
        header.setMessageId(messageId);
        header.setType(Type.KEY_EXCHANGE);
        Info info = new Info(header);
        Tlv tlv = new Tlv(FieldTag.SESSION_KEY_DOWN, key);
        info.addField(tlv);
        return info;
    }

    // 对返回结果进行解析
    public static Info parseResponse(byte[] bytes){
        Info info = new Info();
        info.fromBytes(bytes);
        return info;
    }

}
