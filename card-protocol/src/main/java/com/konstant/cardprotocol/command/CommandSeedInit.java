package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:初始化预置seed
 * 创建人:菜籽
 * 创建时间:2018/4/2 上午10:44
 * 备注:
 */

public class CommandSeedInit extends Command {

    public CommandSeedInit() {
        header.setType(CommandType.SEEDINIT);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of();
    }
}
