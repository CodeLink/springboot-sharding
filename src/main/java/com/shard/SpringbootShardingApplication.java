package com.shard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shard.mapper")
public class SpringbootShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootShardingApplication.class, args);
    }

}
