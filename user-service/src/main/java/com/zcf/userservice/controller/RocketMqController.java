package com.zcf.userservice.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rocketmq")
public class RocketMqController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/produce")
    public String produce() {
        rocketMQTemplate.convertAndSend("zcf-topic", "Hello ZCF!");
        return "发消息成功！";
    }
}
