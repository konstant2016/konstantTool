package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;

/**
 * 描述:获取设备编号
 * 创建人:菜籽
 * 创建时间:2018/4/2 上午10:47
 * 备注:
 */

public class CommandSerialNo extends Command {

    public CommandSerialNo() {
        header.setType(CommandType.SIM_ID);
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return super.getRequiredFields();
    }
}
