package com.zcf.userservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zcf.userservice.feign.OrderServiceFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    /**
     * 限流接口
     * @return
     */
    @RequestMapping("/test")
    public String test() {
        return "test...";
    }

    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping("/getInstances")
    public List<ServiceInstance> getInstances() {
        return discoveryClient.getInstances("order-service");

    }

    /**
     * 手动实现负载均衡
     * @return
     */
    @RequestMapping("/manual-loadbalance")
    public String manualLoadbalance() {
        List<String> uris = discoveryClient.getInstances("order-service").stream()
                .map(instance -> instance.getUri().toString() + "/order/query").collect(Collectors.toList());
        int index = ThreadLocalRandom.current().nextInt(uris.size());

        System.out.println("访问的uri为： " + uris.get(index));
        return uris.get(index);
    }

    // 自动实现负载均衡
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/ribbon-api")
    public String ribbonApi() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("order-service");
        String uri = serviceInstance.getUri().toString() + "/order/query";

        return uri;
    }

    /**
     * 采用restTemple方式请求
     */
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/ribbon-annotation")
    public String ribbonAnnotation() {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("order-service");
//        String url = serviceInstance.getUri().toString() + "/order/query";

        String result = restTemplate.getForObject("http://order-service/order/query", String.class);
        return result;
    }

    @Resource
    private OrderServiceFeignClient orderServiceFeignClient;

    @RequestMapping("/openfeign")
    public String openfeign() {
        return orderServiceFeignClient.query();
    }


    /**
     * 获取yml文件中的配置
     */
    @Value("${student.name}")
    private String studentName;

    /**
     * 本地获取配置文件的值
     * @return
     */
    @RequestMapping("/getValue")
    public String getValue() {
        return studentName;
    }

    /**
     * nacos配置中心获取值
     * @return
     */
    @RequestMapping("/nacos-config")
    public String nacosConfig() {
        return studentName;
    }

    /**
     * 热点规则
     */
    @RequestMapping("/hotrules")
    @SentinelResource("hotrules")
    public String hotRules(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        return "count: " + username + "#" + password;
    }
}
