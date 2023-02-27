package com.konstant.javamodule;

public class RxJava {

    public static void main(String[] args) {
        String abc = "a|b|c|defg";
        String[] split = abc.split("");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}
