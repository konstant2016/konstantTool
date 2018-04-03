package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:切换SIM卡
 * 创建人:菜籽
 * 创建时间:2018/4/2 上午10:53
 * 备注:
 */

public class CommandSwitch extends Command {

    public CommandSwitch() {
        header.setType(CommandType.SWITCH);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of(FieldTag.ICCID);
    }
}
