package com.shard.service.impl;

import com.shard.entity.User;
import com.shard.mapper.UserMapper;
import com.shard.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getList(User user) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery()
                .eq(!Objects.isNull(user.getId()), User::getId, user.getId())
                .eq(StringUtils.isNotEmpty(user.getName()), User::getName, user.getName())
                .eq(!Objects.isNull(user.getCreateTime()), User::getCreateTime, user.getCreateTime())
                .ge(!Objects.isNull(user.getStartTime()), User::getCreateTime, user.getStartTime())
                .le(!Objects.isNull(user.getEndTime()), User::getCreateTime, user.getEndTime());
        return userMapper.selectList(lambdaQuery);
    }

    @Override
    public List<User> between(User user) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery()
                .eq(StringUtils.isNotEmpty(user.getName()), User::getName, user.getName())
                .between(!Objects.isNull(user.getEndTime()) && !Objects.isNull(user.getStartTime()),
                        User::getCreateTime, user.getStartTime(), user.getEndTime());
        return userMapper.selectList(lambdaQuery);
    }
}
