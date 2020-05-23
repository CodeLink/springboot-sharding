package com.shard.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * FB单库分表算法，Precise处理 = 和 in 的路由
 */
public class FBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    private static final Logger log = LoggerFactory.getLogger(FBPreciseShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 取模路由
        return availableTargetNames.stream()
                .filter(tableName -> tableName.endsWith(shardingValue.getValue() % availableTargetNames.size() + ""))
                .findFirst()
                .orElse(null);
    }
}
