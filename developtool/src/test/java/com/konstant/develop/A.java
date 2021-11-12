package com.konstant.develop;

public class A {
    public static void main(String[] args) {
        String url = "https://www.baidu.com";
        String result = UriUtil.addQuery(url, "qsd", "48");
        System.out.println(result);
    }
}
