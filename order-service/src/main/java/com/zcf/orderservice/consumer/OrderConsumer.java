package com.zcf.orderservice.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "zcf-consumer", topic = "zcf-topic")
public class OrderConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("收到消息： " + message);
    }
}
