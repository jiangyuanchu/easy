package com.lomoye.easy.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 杭州蓝诗网络科技有限公司 版权所有 © Copyright 2018<br>
 *
 * @Description: <br>
 * @Project: hades-web <br>
 * @CreateDate: Created in 2018/8/25 <br>
 * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
 */
public class LocalDateUtil {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * @param: null
     * @Return:
     * @Decription: 将LocalDateTime转为自定义的时间格式的字符串
     * @CreateDate: Created in 2018/8/25 18:00
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static String getDateTimeAsString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
        return localDateTime.format(formatter);
    }

    public static String getDateAsString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return localDate.format(formatter);
    }

    public static String getDateAsString(LocalDate localDate, String format) {
        if (localDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    /**
     * @param: timestamp
     * @Return: java.time.LocalDateTime
     * @Decription: 将时间戳转为LocalDateTime
     * @CreateDate: Created in 2018/8/25 18:00
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @param: localDateTime
     * @Return: long
     * @Decription: 将LocalDateTime转为时间戳
     * @CreateDate: Created in 2018/8/25 18:01
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * @param: localDateTime
     * @Return: Date
     * @Decription: 将LocalDateTime转为Date
     * @CreateDate: Created in 2018/8/25 18:01
     * @Author: <a href="yechangjun@quannengzhanggui.cn">lomoye</a>
     * @Modify:
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * @param: localDateTime
     * @Return: Date
     * @Decription: 将Date转为LocalDateTime
     * @CreateDate: Created in 2018/8/25 18:01
     * @Author: <a href="yechangjun@quannengzhanggui.cn">lomoye</a>
     * @Modify:
     */
    public static LocalDateTime fromDate(Date date) {
        if (date == null) {
            return null;
        }

        return getDateTimeOfTimestamp(date.getTime());
    }

    /**
     * 计算时间秒数差值
     */
    public static long getSecondInterval(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);

        return duration.getSeconds();
    }

    /**
     * @param: time
     * @param: format
     * @Return: java.time.LocalDateTime
     * @Decription: 2018-08-30 16:36:00 -> LocalDateTime
     * @CreateDate: Created in  2018/8/25 18:01
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 2018-08-30 16:36:00 -> LocalDateTime 默认的
     *
     * @param time
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
        return LocalDateTime.parse(time, df);
    }

    /**
     * @param: time
     * @Return: java.time.LocalDateTime
     * @Decription: 获取日期的开始时间
     * @CreateDate: Created in 2018/8/28 15:17
     * @Author: <a href="wuchangyi@quannengzhanggui.com">wcy</a>
     * @Modify:
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * @param: time
     * @Return: java.time.LocalDateTime
     * @Decription: 获取日期的最后时刻
     * @CreateDate: Created in 2018/8/28 15:17
     * @Author: <a href="wuchangyi@quannengzhanggui.com">wcy</a>
     * @Modify:
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    /**
     * @param: time
     * @Return: java.time.LocalDateTime
     * @Decription: 获取日期到小时的时间
     * @CreateDate: Created in 2018/9/27 10:52 <br>
     * @Author: <a href="wangyongli@quannengzhanggui.com">stonewyl</a>
     * @Modify:
     */
    public static LocalDateTime getHourTime(LocalDateTime time) {
        return time.withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * @Return:
     * @Decription: 获取日期到小时的时间
     * @CreateDate: Created in 2019-02-15 16:05
     * @Author: <a href="ganpeiwen@quannengzhanggui.cn">ganpeiwen</a>
     * @Modify:
     **/
    public static LocalDateTime getHourEndTime(LocalDateTime time) {
        return time.withMinute(59)
                .withSecond(59)
                .withNano(999);
    }

    /**
     * @param: time
     * @Return: java.time.LocalDateTime
     * @Decription: 获取日期的开始时间
     * @CreateDate: Created in 2018/8/28 15:17
     * @Author: <a href="wuchangyi@quannengzhanggui.com">wcy</a>
     * @Modify:
     */
    public static LocalDateTime getDayStart(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * @param: time
     * @Return: java.time.LocalDateTime
     * @Decription: 获取日期的最后时刻
     * @CreateDate: Created in 2018/8/28 15:17
     * @Author: <a href="wuchangyi@quannengzhanggui.com">wcy</a>
     * @Modify:
     */
    public static LocalDateTime getDayEnd(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * @param: date
     * @Return: java.time.LocalDate
     * @Decription: 获取一个月的开始时间
     * @CreateDate: Created in 2019/2/21 11:01
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDate getMonthStartDay(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * @param: date
     * @Return: java.time.LocalDate
     * @Decription: 获取一个月的结束时间
     * @CreateDate: Created in 2019/2/21 11:01
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDate getMonthEndDay(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * @param: date
     * @Return: java.time.LocalDate
     * @Decription: 获取年的开始时间
     * @CreateDate: Created in 2019/2/21 11:02
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDate getYearStartDay(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * @param: date
     * @Return: java.time.LocalDate
     * @Decription: 获取月的结束时间
     * @CreateDate: Created in 2019/2/21 11:02
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static LocalDate getYearEndDay(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * @param: startTime
     * @param: endTime
     * @Return: long 相差的天数 endTime - startTime
     * @Decription:
     * @CreateDate: Created in 2018/9/10 下午12:30
     * @Author: <a href="yechangjun@quannengzhanggui.com">lomoye</a>
     * @Modify:
     */
    public static long diffDay(LocalDateTime startTime, LocalDateTime endTime) {
        return endTime.toLocalDate().toEpochDay() - startTime.toLocalDate().toEpochDay();
    }

    /**
     * @Return:
     * @Decription: 相差小时数
     * @CreateDate: Created in 2019-01-25 21:09
     * @Author: <a href="ganpeiwen@quannengzhanggui.cn">ganpeiwen</a>
     * @Modify:
     **/
    public static int diffHour(LocalDateTime startTime, LocalDateTime endTime) {
        return endTime.toLocalTime().getHour() - startTime.toLocalTime().getHour();
    }

    /**
     * @param: startTime
     * @param: endTime
     * @Return: long 相差的天数 endTime - startTime
     * @Decription:
     * @CreateDate: Created in 2018/9/10 下午12:30
     * @Author: <a href="yechangjun@quannengzhanggui.com">lomoye</a>
     * @Modify:
     */
    public static long diffDay(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    /**
     * @param: startTime
     * @param: endTime
     * @Return: long
     * @Decription: 相差的月数
     * @CreateDate: Created in 2018/9/14 19:03
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static long diffMonth(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.MONTHS);
    }

    /**
     * @param: startTime
     * @param: endTime
     * @Return: long
     * @Decription: 相差的月数
     * @CreateDate: Created in 2018/9/14 19:17
     * @Author: <a href="wanglili@quannengzhanggui.cn">wangll</a>
     * @Modify:
     */
    public static long diffMonth(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.until(endTime, ChronoUnit.MONTHS);
    }


    /**
     * @Return:
     * @Decription: 获取当天到endTime为止连续的时间列表
     * @CreateDate: Created in 2019-01-25 21:32
     * @Author: <a href="ganpeiwen@quannengzhanggui.cn">ganpeiwen</a>
     * @Modify:
     **/
    public static List<LocalDateTime> continuousTime(LocalDateTime endTime) {
        List<LocalDateTime> continuousTime = new ArrayList<>();
        LocalDateTime startTime = getDayStart(endTime);
        int hour = diffHour(startTime, endTime);
        for (int i = 0; i < hour + 1; i++) {
            LocalDateTime now = getHourTime(startTime).plusHours(i);
            continuousTime.add(now);
        }
        return continuousTime;
    }

    /**
     * ]
     *
     * @Return:
     * @Decription: 获取开始日期到结束日期的连续日期列表
     * @CreateDate: Created in 2019-01-25 21:37
     * @Author: <a href="ganpeiwen@quannengzhanggui.cn">ganpeiwen</a>
     * @Modify:
     **/
    public static List<LocalDate> continuousDate(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> continuousDate = new ArrayList<>();
        long day = diffDay(startDate, endDate);
        for (long i = 0L; i < day + 1L; i++) {
            LocalDate now = startDate.plusDays(i);
            continuousDate.add(now);
        }
        return continuousDate;
    }

}
