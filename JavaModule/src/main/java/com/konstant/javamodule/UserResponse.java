package com.konstant.javamodule;

public class UserResponse {

    private String name;
    private int age;

    public UserResponse() {
    }

    public UserResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
