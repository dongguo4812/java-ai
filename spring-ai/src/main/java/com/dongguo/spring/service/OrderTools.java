package com.dongguo.spring.service;

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
}

