package com.konstant.cardprotocol.thirdtool;

import java.util.HashSet;

/**
 * 描述:不可变集合
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午3:17
 * 备注:
 */

public class ImmutableSet<E> extends HashSet<E> {

    public static <E> ImmutableSet<E> of(E... e) {
        ImmutableSet<E> set = new ImmutableSet<>();
        for (E e1 : e) {
            set.add(e1);
        }
        return set;
    }

}
