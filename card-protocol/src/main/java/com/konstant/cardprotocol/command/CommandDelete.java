package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:删除指令
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午6:06
 * 备注:
 */

public class CommandDelete extends Command{

    public CommandDelete(){
        header.setType(CommandType.DELETE);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of(FieldTag.ICCID);
    }
}
