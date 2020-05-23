package com.shard.controller;

import com.shard.entity.Order;
import com.shard.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 不用分库分表
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("save")
    public Order save(Order order) {
        orderService.save(order);
        return order;
    }

    @PostMapping("getList")
    public List<Order> getList(Order order) {
        return orderService.getList(order);
    }


}
