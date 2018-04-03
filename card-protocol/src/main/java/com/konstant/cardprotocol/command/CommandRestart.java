package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;

/**
 * 描述:重启协议栈
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午6:09
 * 备注:
 */

public class CommandRestart extends Command {

    public CommandRestart() {
        header.setType(CommandType.INIT);
    }
}
