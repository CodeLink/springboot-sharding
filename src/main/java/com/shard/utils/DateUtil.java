package com.shard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateUtil {
    /**
     * 日期格式
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 日期转为字符串，yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String dateToString(final Date date) {
        return dateToString(date, DATE_PATTERN);
    }

    /**
     * 日期转为字符串，自定义格式
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String dateToString(final Date date, final String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串转为日期，yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static Date stringToDate(final String str) {
        return stringToDate(str, DATE_PATTERN);
    }

    /**
     * 字符串转为日期，自定义格式
     *
     * @param dateStr 日期
     * @param pattern 格式
     * @return
     */
    public static Date stringToDate(final String dateStr, final String pattern) {
        if (dateStr == null || "".equals(dateStr.trim())) {
            throw new IllegalArgumentException("The dateStr must not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("dateStr parse fail");
        }
    }

    /**
     * 年份加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(final Date date, final int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 月份加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 周加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(final Date date, final int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 天数加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(final Date date, final int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 小时加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(final Date date, final int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 分钟加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(final Date date, final int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 秒加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 时间加减时分，格式为HH：mm
     *
     * @param date
     * @param str  HH：mm
     * @return
     */
    public static Date addhourMinutes(Date date, String str) {
        if (date == null) {
            date = new Date();
        }
        String[] arry = str.split(":");
        Calendar car = Calendar.getInstance();
        car.setTime(date);
        car.set(Calendar.HOUR, Integer.parseInt(arry[0]));
        car.set(Calendar.MINUTE, Integer.parseInt(arry[1]));
        return car.getTime();
    }

    /**
     * 日期加减
     *
     * @param date          日期
     * @param calendarField 类型(Calendar.YEAR、Calendar.MONTH...)
     * @param amount        数量
     * @return
     */
    private static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 是否同一天（只比较天，不考虑具体时间）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 比较两个日期是否相等(比较毫秒数)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameInstant(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取时间的号数（几号）
     *
     * @param date
     * @return
     */
    public static int getDateOfDay(Date date) {
        return getDate(date, Calendar.DATE);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getDateOfMonth(final Date date) {
        return getDate(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取小时
     *
     * @return
     */
    public static int getDateOfHour(final Date date) {
        return getDate(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public static int getDateOfMinute(final Date date) {
        return getDate(date, Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @param date
     * @return
     */
    public static int getDateOfSecond(final Date date) {
        return getDate(date, Calendar.SECOND);
    }

    /**
     * 获取时间年份
     *
     * @param date
     * @return
     */
    public static int getDateOfYear(Date date) {
        return getDate(date, Calendar.YEAR);
    }

    /**
     * 获取日期=>星期（String）
     *
     * @param date
     * @return
     */
    public static String getDateOfWeek(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = getDate(date, Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取星期数字=>星期（int）<br>
     * <p>
     * 1=>周日 <br>
     * 2=>周一 <br>
     * 6=>周六
     *
     * @param date
     * @return
     */
    public static int getDateOfWeekInt(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        int w = getDate(date, Calendar.DAY_OF_WEEK);

        return w;
    }

    private static int getDate(final Date date, final int calendarField) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(calendarField);

    }

    /**
     * 获取周第一天(从周日开始)
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));
        return c.getTime();
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月总天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonthCount(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期相减得到的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDiffDays(String startDate, String endDate) {
        return getDiffDays(stringToDate(startDate), stringToDate(endDate));
    }

    /**
     * 日期相减得到的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDiffDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        long diff = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }

    /**
     * 日期相减得到的毫秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long dateDiff(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        long date1ms = startDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    /**
     * 获取两个日期中的最大日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Date getMaxDate(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (startDate.after(endDate)) {
            return startDate;
        }
        return endDate;
    }

    /**
     * 获取两个日期中的最小日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Date getMinDate(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (startDate.after(endDate)) {
            return endDate;
        }
        return startDate;
    }

    /**
     * 比较日期大小 <br>
     * 1：第一个日期大，0：两个日期一样，-1：第二个日期大
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int compareDate(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (startDate.getTime() > endDate.getTime()) {
            return 1;
        } else if (startDate.getTime() < endDate.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 计算天数(减去周末)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getLeaveDays(Date startTime, Date endTime) {
        int day = 0;
        // 从startTime开始循环，若该日期不是周六日则请假天数+1
        Date flag = startTime;// 设置循环开始日期
        Calendar cal = Calendar.getInstance();
        // 循环遍历每个日期
        while (flag.compareTo(endTime) != 1) {
            cal.setTime(flag);
            // 判断是否为周六日
            int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (week == 0 || week == 6) {// 0为周日，6为周六
                // 跳出循环进入下一个日期
                cal.add(Calendar.DAY_OF_MONTH, +1);
                flag = cal.getTime();
                continue;
            }
            // 周末，天数+1
            ++day;
            // 日期往后加一天
            cal.add(Calendar.DAY_OF_MONTH, +1);
            flag = cal.getTime();
        }
        return day;
    }

    /**
     * 获取时间段所有月份
     *
     * @param startDate
     * @param endDate
     * @return 月份集合
     * @throws Exception
     */
    public static Set<String> getMonthBetween(String startDate, String endDate) {
        Set<String> result = new HashSet<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(stringToDate(startDate, "yyyy-MM"));
        max.setTime(stringToDate(endDate, "yyyy-MM"));
        max.add(Calendar.MONTH, 1);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(getDateOfMonth(curr.getTime()) + "");
            // 当前日期月份加1
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 获取时间段所有月份
     *
     * @param startDate
     * @param endDate
     * @return 月份集合
     * @throws Exception
     */
    public static Set<String> getMonthBetween(Date startDate, Date endDate) {
        String start = dateToString(startDate);
        String end = dateToString(endDate);
        return getMonthBetween(start, end);
    }

    /**
     * 获取时间段所有年份
     *
     * @param startDate
     * @param endDate
     * @return 年份集合
     * @throws Exception
     */
    public static Set<String> getYearBetween(String startDate, String endDate) {
        Set<String> result = new HashSet<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(stringToDate(startDate, "yyyy"));
        max.setTime(stringToDate(endDate, "yyyy"));
        max.add(Calendar.YEAR, 1);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(getDateOfYear(curr.getTime()) + "");
            // 当前日期月份加1
            curr.add(Calendar.YEAR, 1);
        }
        return result;
    }

    /**
     * 获取时间段所有年份
     *
     * @param startDate
     * @param endDate
     * @return 年份集合
     * @throws Exception
     */
    public static Set<String> getYearBetween(Date startDate, Date endDate) {
        String start = dateToString(startDate);
        String end = dateToString(endDate);
        return getYearBetween(start, end);
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */

    public static LocalDateTime dateToLocalDateTime(final Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

}
