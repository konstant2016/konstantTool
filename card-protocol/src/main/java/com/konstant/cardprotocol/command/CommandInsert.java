package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;

/**
 * 描述:插入SIM卡
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午6:10
 * 备注:
 */

public class CommandInsert extends Command {

    public CommandInsert() {
        header.setType(CommandType.INSERT);
    }
}
