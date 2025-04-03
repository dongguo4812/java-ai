package com.dongguo.spring.service;

import com.dongguo.spring.config.Const;
import com.dongguo.spring.entity.OrderEnum;
import com.dongguo.spring.entity.OrderInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void cancelOrder(String orderNo, String userName) {
        System.out.println("用户：" + userName + "您好,订单号:" + orderNo + "取消成功");
    }

    @Override
    public OrderInfo getOrderInfo(String orderNo, String userName) {

        return new OrderInfo(orderNo, userName, new Date().toString(), new BigDecimal(100), OrderEnum.parse(Const.IntegerNum.ONE).getName());
    }
}
