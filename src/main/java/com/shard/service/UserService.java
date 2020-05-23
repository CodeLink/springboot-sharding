package com.shard.service;

import java.util.List;

import com.shard.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

	List<User> getList(User user);

	/**
	 * RangeShardingAlgorithm范围查询between测试接口
	 * @param user
	 * @return
	 */
	List<User> between(User user);

}
