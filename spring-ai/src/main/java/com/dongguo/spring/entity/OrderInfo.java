package com.dongguo.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInfo {
    private String orderNo;
    private String userName;
    private String orderTime;
    private BigDecimal totalPrice;
    private String status;
}
