package com.zcf.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/query")
    public String query() {
        System.out.println("visited...");
        return "query orders...";
    }
}
