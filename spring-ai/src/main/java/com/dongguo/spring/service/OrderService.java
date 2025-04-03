package com.dongguo.spring.service;

import com.dongguo.spring.entity.OrderInfo;

public interface OrderService {
    /**
     * 模拟退订订单接口
     *
     * @param orderNo
     * @param userName
     */
    void cancelOrder(String orderNo, String userName);

    /**
     * 模拟获取订单接口
     * @param orderNo
     * @param userName
     * @return
     */
    OrderInfo getOrderInfo(String orderNo, String userName);
}
