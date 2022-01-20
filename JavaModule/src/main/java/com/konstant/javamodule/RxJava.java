package com.konstant.javamodule;

public class RxJava {
    public static void main(String[] args) {
        String s = "这是一段测试文字";
        String s1 = AesUtils.encrypt(s, "YangCong345");
        System.out.println("加密后的文字：" + s1);
        String s2 = AesUtils.decrypt(s1, "YangCong345");
        System.out.println("解密后的文字:" + s2);
    }
}
