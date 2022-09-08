package com.konstant.javamodule;

import com.konstant.javamodule.function.YCFormatter;

import java.util.Locale;

public class RxJava {
    public static void main(String[] args) {
        String S = YCFormatter.formatFileSize(8161068L, Locale.US);
        System.out.println(S);
    }
}
