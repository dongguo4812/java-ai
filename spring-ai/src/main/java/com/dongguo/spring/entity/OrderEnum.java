package com.dongguo.spring.entity;

public enum OrderEnum {


    NOT_YET(1, "未支付"),

    PAYLOAD(2, "已支付"),

    PART(3, "部分支付"),

    NULL(-1, "无");

    private final Integer status;

    private final String name;

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    OrderEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static OrderEnum parse(Integer status) {
        for (OrderEnum orderStatus : OrderEnum.values()) {
            if (orderStatus.getStatus().equals(status)) {
                return orderStatus;
            }
        }
        return NULL;
    }

}
