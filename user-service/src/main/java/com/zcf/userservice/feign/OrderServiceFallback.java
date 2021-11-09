package com.zcf.userservice.feign;

import org.springframework.stereotype.Component;

/**
 * feign调用失败处理类
 */
@Component
public class OrderServiceFallback implements OrderServiceFeignClient {
    @Override
    public String query() {
        // 当feign调用失败时， 处理
        System.out.println("服务调用失败，做服务降级处理，mark一下，后续人工补偿。。。");
        return "order service服务调用失败，服务降级";
    }
}
