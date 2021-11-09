package com.zcf.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order-service", fallback = OrderServiceFallback.class)
public interface OrderServiceFeignClient {

    @RequestMapping("/order/query")
    String query();
}
