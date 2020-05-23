package com.shard.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让@MapperScan扫描到！！
 * @param <T>
 */
public interface Mapper<T> extends BaseMapper<T> {
    //这里可以定义通用公共方法

}
