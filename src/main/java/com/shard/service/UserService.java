package com.shard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shard.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> getList(User user);

    /**
     * RangeShardingAlgorithm范围查询between测试接口
     *
     * @param user
     * @return
     */
    List<User> between(User user);

    /**
     * 分库分表-强制路由指定 库、表。针对application-hi配置测试
     *
     * @param user
     * @param database 数据库名
     * @param table    表名
     * @return
     */
    List<User> routStrategy(User user, String database, String table);
}
