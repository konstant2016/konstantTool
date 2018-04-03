package com.konstant.cardprotocol.util;

/**
 * 描述:对字符串进行处理的工具
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午4:04
 * 备注:
 */

public class StringUtils {

    // 数据翻转
    public static String flip(String resource) {

        //如果不是偶数位 ，则直接返回
        if (resource.length() % 2 != 0) {
            return resource;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < resource.length(); i += 2) {
            sb.append(resource.charAt(i + 1)).append(resource.charAt(i));
        }
        return sb.toString();
    }

}
