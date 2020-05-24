package com.shard.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.HashSet;

/**
 * Hint分库,能够绕过Sharding-JDBC SQL解析过程
 */
public final class HintDatabaseShardingAlgorithm implements HintShardingAlgorithm<String> {
    /**
     * @param availableTargetNames
     * @param hintShardingValue    不再从SQL 解析中获取值，通过 hintManager.addDatabaseShardingValue("user", "2021")参数指定
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> hintShardingValue) {
        Collection<String> result = new HashSet<>();
        for (String each : availableTargetNames) {
            for (String value : hintShardingValue.getValues()) {
                if (each.equals(value)) {
                    result.add(each);
                }
            }
        }
        if (result.isEmpty()) {
            throw new IllegalStateException(String.format("no database route with %s", hintShardingValue.getValues().toString()));
        }
        return result;
    }

}
