package com.shard.controller;

import com.shard.entity.User;
import com.shard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分库分表测试
 */
@RestController
@RequestMapping("/shard")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("save")
    public User save(User user) {
        userService.save(user);
        return user;
    }

    @PostMapping("getList")
    public List<User> getList(User user) {
        return userService.getList(user);
    }

    /**
     * RangeShardingAlgorithm范围查询between测试接口
     *
     * @param user
     * @return
     */
    @PostMapping("between")
    public List<User> between(User user) {
        return userService.between(user);
    }

}
