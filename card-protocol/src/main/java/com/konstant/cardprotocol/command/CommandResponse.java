package com.konstant.cardprotocol.command;

import com.konstant.cardprotocol.base.CommandType;
import com.konstant.cardprotocol.base.FieldTag;
import com.konstant.cardprotocol.base.FieldValue;
import com.konstant.cardprotocol.thirdtool.ImmutableSet;
import com.konstant.cardprotocol.thirdtool.Preconditions;

import java.util.Arrays;

/**
 * 描述:通用的返回体
 * 创建人:菜籽
 * 创建时间:2018/4/2 上午10:11
 * 备注:
 */

public class CommandResponse extends Command {

    public CommandResponse() {
        header.setType(CommandType.RESET);
    }

    // 判断返回是否成功
    public boolean isSuccess() {
        Preconditions.checkArgument(isSatisfied());
        byte[] value = getField(FieldTag.RESULT).get().getValue();
        if (Arrays.equals(value, FieldValue.EXECUTE_RESULT_SUCCESS)) {
            return true;
        }
        return false;
    }

    @Override
    protected ImmutableSet<FieldTag> getRequiredFields() {
        return ImmutableSet.of(FieldTag.RESULT);
    }
}
