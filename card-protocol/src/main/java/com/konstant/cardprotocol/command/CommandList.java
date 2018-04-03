package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:获取卡列表
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午6:17
 * 备注:
 */

public class CommandList extends Command {

    public CommandList() {
        header.setType(CommandType.LIST);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of(FieldTag.CARD_LIST);
    }
}
