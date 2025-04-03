package com.dongguo.spring.service;

import com.dongguo.spring.entity.OrderInfo;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class OrderTools {

    private final OrderService orderService;

    /**
     * FunctionCall 方法已弃用
     *
     * @return
     */
    @Bean
    @Description("处理订单退订") // function description
    public Function<CancelOrderRequest, String> cancelOrder() {
        return cancelOrderRequest -> {
            //调用退订方法apply
            orderService.cancelOrder(cancelOrderRequest.orderNo, cancelOrderRequest.userName);
            return "退订成功";
        };
    }

    /**
     * 密封类
     *
     * @param orderNo
     * @param userName
     */
    @JsonClassDescription("处理订单退订")
    public record CancelOrderRequest(String orderNo, String userName) {
    }

    /**
     *
     * @return
     */
    @Bean
    @Description("获取订单信息") //
    public Function<CancelOrderRequest, OrderInfo> getOrderInfo() {
        return OrderRequest -> {
            //调用订单信息apply
            return orderService.getOrderInfo(OrderRequest.orderNo, OrderRequest.userName);
        };
    }

    /**
     * 密封类
     *
     * @param orderNo
     * @param userName
     */
    @JsonClassDescription("处理订单退订")
    public record OrderRequest(String orderNo, String userName) {
    }
}

