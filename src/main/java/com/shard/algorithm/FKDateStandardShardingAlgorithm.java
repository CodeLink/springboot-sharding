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
 * FK分表算法，按月份分表
 * <br>
 * 1月份==》user_1 <br>
 * 2月份==》user_2 <br>
 * 3月份==》user_3 <br>
 */
public class FKDateStandardShardingAlgorithm implements RangeShardingAlgorithm<Date>, PreciseShardingAlgorithm<Date> {

    private static final Logger log = LoggerFactory.getLogger(FKDateStandardShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        // 月份路由
        return availableTargetNames.stream()
                .filter(tableName -> tableName.endsWith(DateUtil.getDateOfMonth(shardingValue.getValue()) + ""))
                .findFirst()
                .orElse(null);
    }

    /**
     * 这里有个坑，当我们查询2020-01-01至2021-01-01时，会查询数据库shardfk_2020和shardfk_2021的所有表，
     * 对于shardfk_2021这个库，我们只需要查询user_1这个表即可，现在多查询了两个表，不知道shardingJdbc支不支持对每个库设置不同表？
     * 如果无法解决上诉问题，就没必要配置分表的RangeShardingAlgorithm范围查询
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        // 范围参数
        Date lower = shardingValue.getValueRange().lowerEndpoint();
        Date upper = shardingValue.getValueRange().upperEndpoint();
        Collection<String> routTables = getRoutTable(availableTargetNames, lower, upper);
        return routTables;
    }

    /**
     * 匹配对应表名
     *
     * @param tableList
     * @param lower
     * @param upper
     * @return
     */
    private Collection<String> getRoutTable(Collection<String> tableList, Date lower, Date upper) {
        Set<String> routTables = new HashSet<String>();
        if (lower != null && upper != null) {
            for (String tableName : tableList) {
                Set<String> monthList = DateUtil.getMonthBetween(lower, upper);
                monthList.forEach(mon -> {
                    if (tableName.endsWith(mon)) {
                        routTables.add(tableName);
                    }
                });
            }
        }
        return routTables;
    }
}
