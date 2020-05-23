package com.shard.algorithm;

import com.shard.utils.DateUtil;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * FB单库分表算法，按月分范围分片。RangeShardingAlgorithm用于处理使用单一键作为分片键的between进行分片的场景
 * <br>
 * 1月份==》user_1 <br>
 * 2月份==》user_2 <br>
 * 3月份==》user_3 <br>
 */
public class FBDateStandardShardingAlgorithm implements RangeShardingAlgorithm<Date>, PreciseShardingAlgorithm<Date> {

    private static final Logger log = LoggerFactory.getLogger(FBDateStandardShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        // 月份路由
        return availableTargetNames.stream()
                .filter(tableName -> tableName.endsWith(DateUtil.getDateOfMonth(shardingValue.getValue()) + ""))
                .findFirst()
                .orElse(null);
    }

    /**
     * 分片键between操作触发
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         RangeShardingValue<Date> shardingValue) {
        // 月份范围
        Date lower = shardingValue.getValueRange().lowerEndpoint();
        Date upper = shardingValue.getValueRange().upperEndpoint();
        Collection<String> routTables = getRoutTable(shardingValue.getLogicTableName(), lower, upper);
        return routTables;
    }

    /**
     * 匹配对应表名
     *
     * @param logicTable
     * @param lowerEnd
     * @param upperEnd
     * @return
     */
    private Collection<String> getRoutTable(String logicTable, Date lowerEnd, Date upperEnd) {
        Set<String> routTables = new HashSet<String>();
        if (lowerEnd != null && upperEnd != null) {
            List<String> result = Lists.newArrayList();
            Date startDate = lowerEnd;
            while (DateUtil.compareDate(startDate, upperEnd) == -1) {// 判断是否到结束日期
                // 表后缀
                String str = DateUtil.getDateOfMonth(startDate) + "";
                result.add(str);
                // 当前日期月份加1
                startDate = DateUtil.addMonths(startDate, 1);
            }
            routTables = result.stream().map(str -> logicTable + "_" + str).collect(Collectors.toSet());
        }
        return routTables;
    }
}
