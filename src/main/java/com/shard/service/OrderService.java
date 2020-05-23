package com.shard.service;

import com.shard.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderService extends IService<Order> {

    List<Order> getList(Order order);

}
