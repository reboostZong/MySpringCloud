package com.zcf.userservice;

import com.zcf.userservice.bean.Person;
import com.zcf.userservice.enable.EnableZcf;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableFeignClients  // 开启openFeign的能力
@EnableZcf
public class UserServiceApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    /**
     * restTemplate必须手动注入 IOC容器
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public Person persona() {
        return new Person("a");
    }

    @Bean
    public Person personb() {
        return new Person("b");
    }

    @Bean
    public Person personc() {
        return new Person("c");
    }

    @Resource
    @LoadBalanced
    public List<Person> persons = Collections.emptyList();

    /**
     * 该方法在IOC容器初始化之后，因此可以打印IOC容器内的内容
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(persons);

    }
}
