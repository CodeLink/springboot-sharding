package com.shard.service.impl;

import com.shard.entity.Order;
import com.shard.mapper.OrderMapper;
import com.shard.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> getList(Order order) {
        LambdaQueryWrapper<Order> lambdaQuery = Wrappers.<Order>lambdaQuery()
                .eq(!Objects.isNull(order.getId()), Order::getId, order.getId())
                .eq(StringUtils.isNotEmpty(order.getName()), Order::getName, order.getName())
                .between(!Objects.isNull(order.getEndTime()) && !Objects.isNull(order.getStartTime()),
                        Order::getCreateTime, order.getStartTime(), order.getEndTime());
        return orderMapper.selectList(lambdaQuery);
    }
}
