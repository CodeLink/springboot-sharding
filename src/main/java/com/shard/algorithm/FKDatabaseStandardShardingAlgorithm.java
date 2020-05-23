package com.shard.algorithm;

import com.shard.utils.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FK分库算法，按年份分库。
 * <br>
 * 2020年==》shardfk_2020数据库
 * <br>
 * 2021年==》shardfk_2021数据库
 */
public class FKDatabaseStandardShardingAlgorithm implements RangeShardingAlgorithm<Date>, PreciseShardingAlgorithm<Date> {

    private static final Logger log = LoggerFactory.getLogger(FKDatabaseStandardShardingAlgorithm.class);

    /**
     * 分片键bwtween操作触发
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         RangeShardingValue<Date> shardingValue) {
        // 年份范围
        Date lower = shardingValue.getValueRange().lowerEndpoint();
        Date upper = shardingValue.getValueRange().upperEndpoint();
        Collection<String> routDatabase = getRoutTDataBase(availableTargetNames, lower, upper);
        return routDatabase;
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        // 年份路由
        return availableTargetNames.stream()
                .filter(databaseName -> databaseName.endsWith(DateUtil.getDateOfYear(shardingValue.getValue()) + ""))
                .findFirst()
                .orElse(null);
    }

    /**
     * 匹配对应数据库名
     *
     * @param databaseList 数据库集合
     * @param lower        开始时间
     * @param upper        结束时间
     * @return
     */
    private Collection<String> getRoutTDataBase(Collection<String> databaseList, Date lower, Date upper) {
        Set<String> routDatabase = new HashSet<String>();
        if (lower != null && upper != null) {
            for (String databaseName : databaseList) {
                Set<String> yearList = DateUtil.getYearBetween(lower, upper);
                yearList.forEach(mon -> {
                    if (databaseName.endsWith(mon)) {
                        routDatabase.add(databaseName);
                    }
                });
            }
        }
        return routDatabase;
    }
}
