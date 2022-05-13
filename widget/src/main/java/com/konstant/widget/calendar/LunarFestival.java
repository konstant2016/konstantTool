package com.konstant.widget.calendar;

import android.util.Pair;

import java.util.List;

import cn.hutool.core.date.chinese.LunarInfo;
import cn.hutool.core.map.TableMap;

public class LunarFestival {
    private static final TableMap<Pair<Integer, Integer>, String> L_FTV = new TableMap(16);

    public LunarFestival() {
    }

    public static List<String> getFestivals(int year, int month, int day) {
        if (12 == month && 29 == day && 29 == LunarInfo.monthDays(year, month)) {
            ++day;
        }

        return getFestivals(month, day);
    }

    public static List<String> getFestivals(int month, int day) {
        return L_FTV.getValues(new Pair(month, day));
    }

    static {
        L_FTV.put(new Pair(1, 1), "春节");
        L_FTV.put(new Pair(1, 2), "犬日");
        L_FTV.put(new Pair(1, 3), "猪日");
        L_FTV.put(new Pair(1, 4), "羊日");
        L_FTV.put(new Pair(1, 5), "牛日");
        L_FTV.put(new Pair(1, 6), "马日");
        L_FTV.put(new Pair(1, 7), "人日");
        L_FTV.put(new Pair(1, 8), "谷日");
        L_FTV.put(new Pair(1, 9), "天日");
        L_FTV.put(new Pair(1, 10), "地日");
        L_FTV.put(new Pair(1, 12), "火日");
        L_FTV.put(new Pair(1, 13), "上灯日");
        L_FTV.put(new Pair(1, 15), "元宵节");
        L_FTV.put(new Pair(1, 18), "落灯日");
        L_FTV.put(new Pair(2, 1), "中和节");
        L_FTV.put(new Pair(2, 2), "龙抬头");
        L_FTV.put(new Pair(2, 12), "花朝节");
        L_FTV.put(new Pair(2, 19), "观世音圣诞");
        L_FTV.put(new Pair(3, 3), "上巳节");
        L_FTV.put(new Pair(4, 1), "祭雹神");
        L_FTV.put(new Pair(4, 4), "文殊菩萨诞辰");
        L_FTV.put(new Pair(4, 8), "佛诞节");
        L_FTV.put(new Pair(5, 5), "端午节");
        L_FTV.put(new Pair(6, 6), "晒衣节");
        L_FTV.put(new Pair(6, 6), "天贶节");
        L_FTV.put(new Pair(6, 24), "火把节");
        L_FTV.put(new Pair(7, 7), "七夕");
        L_FTV.put(new Pair(7, 14), "鬼节(南方)");
        L_FTV.put(new Pair(7, 15), "中元节");
        L_FTV.put(new Pair(7, 30), "地藏节");
        L_FTV.put(new Pair(8, 15), "中秋节");
        L_FTV.put(new Pair(9, 9), "重阳节");
        L_FTV.put(new Pair(10, 1), "祭祖节");
        L_FTV.put(new Pair(10, 15), "下元节");
        L_FTV.put(new Pair(11, 17), "阿弥陀佛圣诞");
        L_FTV.put(new Pair(12, 8), "腊八节");
        L_FTV.put(new Pair(12, 16), "尾牙");
        L_FTV.put(new Pair(12, 23), "小年");
        L_FTV.put(new Pair(12, 30), "除夕");
    }
}