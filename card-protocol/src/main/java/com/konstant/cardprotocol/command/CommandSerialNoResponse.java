package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:获取设备串号的返回指令
 * 创建人:菜籽
 * 创建时间:2018/4/2 上午10:49
 * 备注:
 */

public class CommandSerialNoResponse extends Command {

    public CommandSerialNoResponse() {
        header.setType(CommandType.SIM_ID_RESPONSE);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of(FieldTag.CHIP_ID);
    }
}
