package com.dongguo.spring.entity;

public enum ChatTypeEnum {
    CHAT("chat");


    private String type;

    ChatTypeEnum(String type) {

        this.type = type;
    }

    public String getType() {
        return type;
    }
}
