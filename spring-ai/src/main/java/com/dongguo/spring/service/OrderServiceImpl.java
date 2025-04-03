package com.dongguo.spring.service;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String cancelOrder(String orderNo, String userName) {
        System.out.println("用户：" + userName + "您好,订单号:" + orderNo + "取消成功");
        return "";
    }
}
