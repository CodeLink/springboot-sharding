package com.shard.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间入参格式化
 */
@Component
public class StringDateConverter implements Converter<String, Date> {
    private static final List<String> FORMARTS = new ArrayList<>(8);

    static {
        FORMARTS.add("yyyy-MM");
        FORMARTS.add("yyyy-MM-dd");
        FORMARTS.add("yyyy-MM-dd hh:mm");
        FORMARTS.add("yyyy-MM-dd hh:mm:ss");
        FORMARTS.add("yyyy/MM");
        FORMARTS.add("yyyy/MM/dd");
        FORMARTS.add("yyyy/MM/dd hh/mm");
        FORMARTS.add("yyyy/MM/dd hh/mm/ss");

    }

    @Override
    public Date convert(String source) {
        if (source == null || "".equals(source.trim())) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{2}$")) {
            return parseDate(source, FORMARTS.get(0));
        } else if (source.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return parseDate(source, FORMARTS.get(1));
        } else if (source.matches("^\\d{4}-\\d{2}-\\d{2} {1}\\d{2}:\\d{2}$")) {
            return parseDate(source, FORMARTS.get(2));
        } else if (source.matches("^\\d{4}-\\d{2}-\\d{2} {1}\\d{2}:\\d{2}:\\d{2}$")) {
            return parseDate(source, FORMARTS.get(3));
        } else if (source.matches("^\\d{4}/\\d{2}$")) {
            return parseDate(source, FORMARTS.get(4));
        } else if (source.matches("^\\d{4}/\\d{2}/\\d{2}$")) {
            return parseDate(source, FORMARTS.get(5));
        } else if (source.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}$")) {
            return parseDate(source, FORMARTS.get(6));
        } else if (source.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$")) {
            return parseDate(source, FORMARTS.get(7));
        } else {
            throw new IllegalArgumentException("日期格式转换错误：" + source);
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}