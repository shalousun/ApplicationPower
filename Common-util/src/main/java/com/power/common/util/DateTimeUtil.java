package com.power.common.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author sunyu
 */
public class DateTimeUtil {

    public static final String DATE_FORMAT_MINITE = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SECOND_12 = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_CHINESE_SECONDE = "yyyy年MM月dd日 HH:mm:ss";
    public static final String DATE_FORMAT_CHINESE_WEEK_SECONDE = "yyyy年MM月dd日 E HH:mm:ss";
    public static final String DATE_FORMAT_ZONED_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String DATE_MINUTES_12 = "yyyy-MM-dd hh-mm-ss";
    public static final String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final long DAY_MS = 86400000L;
    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();
    private static final int PATTERN_CACHE_SIZE = 500;
    private final static String[] WEEK_ARR = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * Formats a java.util.Date using a format string
     *
     * @param date   The date to format
     * @param format The format string like yyyy-MM-dd
     * @return String
     */
    public static String dateToStr(Date date, String format) {
        return dateToStr(date, format, null);
    }

    /**
     * now timestamp
     *
     * @return String
     */
    public static String nowStrTime() {
        return long2Str(System.currentTimeMillis(), DATE_FORMAT_SECOND);
    }

    /**
     * get str now time
     *
     * @param pattern pattern like yyyy-MM-dd
     * @return String
     */
    public static String nowStrTime(String pattern) {
        return long2Str(System.currentTimeMillis(), pattern);
    }

    /**
     * 12-hour clock
     *
     * @return String
     */
    public static String nowStrTime12() {
        return long2Str(System.currentTimeMillis(), DATE_MINUTES_12);
    }

    /**
     * Obtain now date
     *
     * @return String
     */
    public static String nowStrDate() {
        return LocalDate.now().toString();
    }

    /**
     * Format ZonedDateTime to String
     *
     * @param zonedDateTime ZonedDateTime
     * @param format        Time format
     * @return String
     */
    public static String zonedDateTimeToStr(ZonedDateTime zonedDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(zonedDateTime);
    }

    /**
     * Convert Date to String
     *
     * @param date    java.util.Date
     * @param pattern pattern like yyyy-MM-dd
     * @param locale  locale
     * @return String
     */
    public static String dateToStr(Date date, String pattern, Locale locale) {
        return localDateTimeToStr(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

    /**
     * Formats a java.sql.Date using a format string
     *
     * @param date   java.sql.Date
     * @param format format like yyyy-MM-dd
     * @return String
     */
    public static String sqlDateToStr(java.sql.Date date, String format) {
        return localDateTimeToStr(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), format);
    }


    /**
     * Parses a date using a specified format string
     *
     * @param sDate  String
     * @param format String
     * @return Date
     */
    public static Date strToDate(String sDate, String format) {
        LocalDate localDate = parseLocalDate(sDate, format);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * Generation timestamp
     *
     * @return java.sql.Timestamp
     */
    public static Timestamp nowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * format a string time like '2013-02-13' to '2013-02-13 00:00:00.0'
     *
     * @param date String
     * @return java.sql.Timestamp
     */
    public static Timestamp strToStamp(String date) {
        return strToStamp(date, DATE_FORMAT_SECOND);
    }

    /**
     * Convert String to Timestamp
     *
     * @param date   String of date content
     * @param format data format
     * @return Timestamp
     */
    public static Timestamp strToStamp(String date, String format) {
        LocalDateTime localDateTime = parseLocalDateTime(date, format);
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * input began time and end time calculate run time
     *
     * @param beginTime long
     * @param endTime   long
     * @return double
     */
    public static double calcRunTime(long beginTime, long endTime) {
        return endTime - beginTime;
    }

    public static void printRunTime(long beganTime, long endTime) {
        System.out.println("CostTime->" + (endTime - beganTime) / 1000.00
                + " Seconds");
    }

    /**
     * obtain now time
     *
     * @return Long
     */
    public static Long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * Get Distance Of ms between and two date
     *
     * @param before before date
     * @param after  after date
     * @return long
     */
    public static long getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }


    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    /**
     * format TimeStamp to string
     *
     * @param time java.sql.Timestamp
     * @param fmt  String
     * @return String
     */
    public static String timestampToString(Timestamp time, String fmt) {
        DateTimeFormatter dateTimeFormatter = createCacheFormatter(fmt);
        LocalDateTime dateTime = time.toLocalDateTime();
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * set time to next day's 0 hour 0 minute 0 second
     *
     * @param time java.sql.Timestamp
     * @return long
     */
    public static long setTimeToNextDay0H0M0S(Timestamp time) {
        if (time != null) {
            return setTimeToNextDay0H0M0S(time.getTime());
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * set time to next day's 0 hour 0 minute 0 second
     *
     * @param millis millisecond
     * @return long
     */
    public static long setTimeToNextDay0H0M0S(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.plusDays(1);
        return localDateToLong(localDate);
    }

    /**
     * if the time is today return current milliseconds else set time to next
     * day's 0 hour 0 minute 0 second then return except today
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToNextDay0H0M0SExceptToday(long millis) {
        long finalTime;
        boolean flag = isDifferentDay(System.currentTimeMillis(), millis);
        if (flag) {
            finalTime = setTimeToNextDay0H0M0S(millis);
        } else {
            finalTime = System.currentTimeMillis();
        }
        return finalTime;
    }

    /**
     * if the time is today return current milliseconds else set time to next
     * day's 0 hour 0 minute 0 second then return except today
     *
     * @param stamp java.sql.Timestamp
     * @return long
     */
    public static long setToNextDay0H0M0SExceptToday(Timestamp stamp) {
        long finalTime;
        if (Objects.nonNull(stamp)) {
            boolean flag = isDifferentDay(nowTimeStamp(), stamp);
            if (flag) {
                finalTime = setTimeToNextDay0H0M0S(stamp);
            } else {
                finalTime = System.currentTimeMillis();
            }
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
        return finalTime;
    }

    /**
     * set time to 0 hour 0 minute 0 second
     *
     * @param time java.sql.Timestamp
     * @return long
     */
    public static long setTimeTo0H0M0S(Timestamp time) {
        if (Objects.nonNull(time)) {
            return setTimeTo0H0M0S(time.getTime());
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * set time to 0 hour 0 minute 0 second
     *
     * @param millis long
     * @return long
     */
    public static long setTimeTo0H0M0S(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        return localDateToLong(localDate);

    }

    /**
     * set time to last day's 0 hour 0 minute 0 second
     *
     * @param time java.sql.Timestamp
     */
    public static void setTimeToLastDay0H0M0S(Timestamp time) {
        if (Objects.nonNull(time)) {
            LocalDate localDate = longToLocalDate(time.getTime());
            localDate = localDate.plusDays(1);
            time.setTime(localDateToLong(localDate));
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * Different day
     *
     * @param timestamp0 java.sql.Timestamp
     * @param timestamp1 java.sql.Timestamp
     * @return boolean
     */
    public static boolean isDifferentDay(Timestamp timestamp0, Timestamp timestamp1) {
        if (Objects.isNull(timestamp0) || Objects.isNull(timestamp1)) {
            throw new NullPointerException("Timestamp can not be null");
        } else {
            return isDifferentDay(timestamp0.getTime(), timestamp1.getTime());
        }
    }

    /**
     * Different day
     *
     * @param millis0 millisecond
     * @param millis1 millisecond
     * @return boolean
     */
    public static boolean isDifferentDay(long millis0, long millis1) {
        LocalDate localDate0 = longToLocalDate(millis0);
        LocalDate localDate1 = longToLocalDate(millis1);
        return isDifferentDay(localDate0, localDate1);
    }

    /**
     * Different day
     *
     * @param localDate0 LocalDate
     * @param localDate1 LocalDate
     * @return boolean
     */
    public static boolean isDifferentDay(LocalDate localDate0, LocalDate localDate1) {
        return !localDate0.equals(localDate1);
    }

    /**
     * Convert millisecond to String like (yyyy-MM-dd)
     *
     * @param millSec millisecond
     * @return String
     */
    public static String long2Str(Long millSec) {
        return long2Str(millSec, DATE_FORMAT_CHINESE);
    }

    /**
     * Convert millisecond to String with format
     *
     * @param millSec millisecond
     * @param format  like yyyy-MM-dd
     * @return String
     */
    public static String long2Str(long millSec, String format) {
        return long2Str(millSec, format, Locale.CHINESE);
    }

    /**
     * Convert millisecond to String
     *
     * @param millSec millisecond
     * @param format  like yyyy-MM-dd
     * @param locale  locale
     * @return String
     */
    public static String long2Str(long millSec, String format, Locale locale) {
        return localDateTimeToStr(LocalDateTime.ofInstant(Instant.ofEpochMilli(millSec), ZoneId.systemDefault()), format);
    }

    /**
     * transfer string to long
     *
     * @param strTime String
     * @param pattern String
     * @return long
     */
    public static long strToLong(String strTime, String pattern) {
        try {
            return LocalDateTimeToLong(parseLocalDateTime(strTime, pattern));
        } catch (Exception e) {
            //ignore
        }
        return localDateToLong(parseLocalDate(strTime, pattern));
    }


    /**
     * Pasted millisecond
     *
     * @param millions millisecond
     * @return long
     */
    public static long todayPastMillisecond(long millions) {
        Calendar cal = initCalenderWithMillis(millions);
        return cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60;
    }

    /**
     * Get days of current month
     *
     * @return int
     */
    public static int getDayOfMonth() {
        return LocalDate.now().lengthOfMonth();
    }

    /**
     * Get day of month
     *
     * @param stamp java.sql.Timestamp
     * @return int
     */
    public static int getLengthOfMonth(Timestamp stamp) {
        if (Objects.nonNull(stamp)) {
            return getLengthOfMonth(stamp.getTime());
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * Get length of month
     *
     * @param ms millisecond
     * @return int
     */
    public static int getLengthOfMonth(long ms) {
        LocalDate localDateTime = longToLocalDate(ms);
        return getLengthOfMonth(localDateTime);
    }

    /**
     * Get day of Month
     *
     * @param localDate LocalDate
     * @return int
     */
    public static int getLengthOfMonth(LocalDate localDate) {
        if (Objects.nonNull(localDate)) {
            return localDate.lengthOfMonth();
        } else {
            throw new NullPointerException("LocalDate can not be null");
        }
    }

    /**
     * First day of week
     *
     * @param stamp java.sql.Timestamp
     * @return long
     */
    public static long getFirstDayOfCurrentWeek(Timestamp stamp) {
        if (null != stamp) {
            return getFirstDayOfCurrentWeek(stamp.getTime());
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * First day of week
     *
     * @param ms millisecond
     * @return long
     */
    public static long getFirstDayOfCurrentWeek(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        TemporalField fieldIso = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        localDate = localDate.with(fieldIso, 1);
        return localDateToLong(localDate);
    }


    /**
     * First day of next year
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToFirstDayOfCurrentYear(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.with(TemporalAdjusters.firstDayOfYear());
        return localDateToLong(localDate);
    }

    /**
     * First day of next year
     *
     * @param millis millisecond
     * @return long
     */

    public static long setToFirstDayOfNextYear(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.with(TemporalAdjusters.firstDayOfNextYear());
        return localDateToLong(localDate);
    }

    /**
     * First day of last month
     *
     * @param ms millisecond
     * @return millisecond
     */
    public static long setToFirstDayOfLastMonth(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        localDate = localDate.minusMonths(1);
        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return localDateToLong(localDate);
    }

    /**
     * 上月同期
     *
     * @param ms millisecond
     * @return millisecond
     */
    public static long setToLastMonthCommonDay(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        localDate = localDate.minusMonths(1);
        return localDateToLong(localDate);
    }

    /**
     * First day of month
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToFirstDayOfCurrentMonth(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return localDateToLong(localDate);
    }

    /**
     * First day of next month
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToFirstDayOfNextMonth(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        return localDateToLong(localDate);
    }

    /**
     * Next year common day
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToNextYearCommonDay(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.plusYears(1);
        return localDateToLong(localDate);
    }

    /**
     * Last year common day
     *
     * @param millis millisecond
     * @return long
     */
    public static long setToLastYearCommonDay(long millis) {
        LocalDate localDate = longToLocalDate(millis);
        localDate = localDate.minusYears(1);
        return localDateToLong(localDate);
    }

    /**
     * Last day of week
     *
     * @param stamp java.sql.Timestamp
     * @return long
     */
    public static long getLastDayOfCurrentWeek(Timestamp stamp) {
        LocalDate localDate = longToLocalDate(stamp.getTime());
        TemporalField fieldIso = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        localDate = localDate.with(fieldIso, 7);
        return localDateToLong(localDate);
    }

    /**
     * First day of current Quarter
     *
     * @param ms millisecond
     * @return long
     */
    public static long getFirstDayOfCurrentQuarter(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        Month month = localDate.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        localDate = LocalDate.of(localDate.getYear(), firstMonthOfQuarter, 1);
        return localDateToLong(localDate);
    }

    /**
     * First day of next Quarter
     *
     * @param ms millisecond
     * @return long
     */
    public static long getFirstDayOfNextQuarter(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        Month month = localDate.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 3);
        localDate = LocalDate.of(localDate.getYear(), endMonthOfQuarter, 1);
        return localDateToLong(localDate);
    }

    /**
     * Get day of week
     *
     * @param ms millisecond
     * @return int
     */
    public static int getDayOfWeek(long ms) {
        LocalDate plainDate = longToLocalDate(ms);
        return plainDate.getDayOfWeek().getValue();
    }

    /**
     * Get start millisecond of current day
     *
     * @return long
     */
    public static long create0H0M0STime() {
        return setTimeTo0H0M0S(System.currentTimeMillis());
    }

    /**
     * Is Today
     *
     * @param ms millisecond
     * @return boolean
     */
    public static boolean isToday(long ms) {
        LocalDate nowDate = LocalDate.now();
        LocalDate plainDate = longToLocalDate(ms);
        return nowDate.equals(plainDate);
    }

    /**
     * Friendly time for chinese
     *
     * @param ms millisecond
     * @return String
     */
    public static String friendlyTime(long ms) {
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        if (isDifferentDay(System.currentTimeMillis(), ms)) {
            int hour = (int) ((cal.getTimeInMillis() - ms) / 3600000);
            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - ms) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        }
        long lt = ms / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - ms) / 3600000);
            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - ms) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = long2Str(ms);
        }
        return ftime;
    }

    /**
     * Distance of week
     *
     * @param startTime millisecond
     * @param endTime   millisecond
     * @return Integer
     */
    public static int getWeeks(long startTime, long endTime) {
        int temp = 0;
        try {
            temp = (int) ((endTime - startTime) / 86400000 / 7);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return temp;
    }

    public static String getDateWithWeek(long ms) {
        Calendar cal = initCalenderWithMillis(ms);
        int a = cal.get(Calendar.DAY_OF_WEEK);
        String[] weekARR = {"（周日）", "（周一）", "（周二）", "（周三）", "（周四）", "（周五）", "（周六）"};
        return long2Str(ms, "MM月dd日" + weekARR[a - 1]);
    }

    public static String getDateWithWeekAndTime(long ms) {
        Calendar cal = initCalenderWithMillis(ms);
        int a = cal.get(Calendar.DAY_OF_WEEK);
        return long2Str(ms, "yyyy年MM月dd日 " + WEEK_ARR[a - 1] + " HH:mm");
    }

    /**
     * calc age,if born in the future return -1
     *
     * @param ms long
     * @return int
     */
    public static int getAge(long ms) {
        LocalDateTime born = longToLocalDateTime(ms);
        if (born.isAfter(LocalDateTime.now())) {
            return -1;
        }
        long until = born.until(LocalDateTime.now(), ChronoUnit.YEARS);
        return (int) until;
    }

    /**
     * 去年同期
     *
     * @param strDate String of date
     * @param format  time format
     * @return String
     */
    public static String getLastYearCommonDay(String strDate, String format) {
        long ms = strToLong(strDate, format);
        return DateTimeUtil.long2Str(setToLastYearCommonDay(ms), format);
    }

    /**
     * Last month common day
     *
     * @param strDate strDate
     * @param format  time format
     * @return String
     */
    public static String getLastMonthCommonDay(String strDate, String format) {
        long ms = strToLong(strDate, format);
        return long2Str(setToLastMonthCommonDay(ms), format);
    }

    /**
     * get birthday from id card
     *
     * @param idCard idCard
     * @return Timestamp
     */
    public static Timestamp getBirthdayFormIdCard(String idCard) {
        Timestamp timestamp;
        if (ValidateUtil.isIdCard(idCard)) {
            if (idCard.length() == 18) {
                timestamp = strToStamp(idCard.substring(6, 14), "yyyyMMdd");
            } else {
                timestamp = strToStamp(idCard.substring(6, 12), "yyyyMMdd");
            }
        } else {
            throw new RuntimeException("invalid IdChard number:" + idCard);
        }
        return timestamp;
    }

    /**
     * get birthday from chinese idcard
     *
     * @param idCard chinese idcard
     * @return long
     */
    public static long getLongBirthFormIdCard(String idCard) {
        return getBirthdayFormIdCard(idCard).getTime();
    }

    /**
     * Is current year
     *
     * @param ms millisecond
     * @return boolean
     */
    public static boolean isCurrentYear(long ms) {
        LocalDate localDate = longToLocalDate(ms);
        int yearTemp = localDate.getYear();
        LocalDate calNow = LocalDate.now();
        int yearNow = calNow.getYear();
        return yearNow == yearTemp;
    }


    /**
     * Convert LocalDateTime to String
     *
     * @param localDateTime localDateTime
     * @param pattern       pattern
     * @return String
     */
    public static String localDateTimeToStr(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * Create DateTimeFormatter in cache
     *
     * @param pattern pattern
     */
    private static DateTimeFormatter createCacheFormatter(String pattern) {
        if (Objects.isNull(pattern) || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if (formatter == null) {
            if (FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE) {
                formatter = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
                if (oldFormatter != null) {
                    formatter = oldFormatter;
                }
            }
        }
        return formatter;
    }

    /**
     * Convert Long to LocalDate
     *
     * @param ms millisecond
     * @return LocalDate
     */
    public static LocalDate longToLocalDate(long ms) {
        return Instant.ofEpochMilli(ms).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    /**
     * Convert Long to LocalDate
     *
     * @param ms millisecond
     * @return LocalDateTime
     */
    public static LocalDateTime longToLocalDateTime(long ms) {
        return Instant.ofEpochMilli(ms).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * convert localDate to long
     *
     * @param localDate LocalDate
     * @return long
     */
    public static long localDateToLong(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    /**
     * Convert String to LocalDate
     *
     * @param time    time
     * @param pattern pattern
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String time, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDate.parse(time, formatter);
    }

    /**
     * Convert String to LocalDateTime
     *
     * @param time    formatted string
     * @param pattern format
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * Convert LocalDateTime to Long
     *
     * @param dateTime LocalDateTime
     * @return long
     */
    public static long LocalDateTimeToLong(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * Convert UTC to CST
     *
     * @param utc    utc time
     * @param format out time format
     * @return String
     */
    public static String UTCToCST(String utc, String format) {
        ZonedDateTime zdt = ZonedDateTime.parse(utc);
        LocalDateTime localDateTime = zdt.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDateTime.plusHours(8));
    }

    /**
     * Convert CST to UTC
     *
     * @param time        time
     * @param inputFormat input time format
     * @param outFormat   out time format
     * @return String
     */
    public static String CTSToUTC(String time, String inputFormat, String outFormat) {
        OffsetDateTime utcTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(inputFormat))
                .atZone(ZoneId.systemDefault())
                .toOffsetDateTime()
                .withOffsetSameInstant(ZoneOffset.UTC);
        return utcTime.format(DateTimeFormatter.ofPattern(outFormat));
    }

    /**
     * Convert CST to UTC
     *
     * @param timestamp timestamp
     * @param outFormat out format
     * @return String
     */
    public static String CTSToUTC(long timestamp, String outFormat) {
        OffsetDateTime utcTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                .atZone(ZoneId.systemDefault())
                .toOffsetDateTime()
                .withOffsetSameInstant(ZoneOffset.UTC);
        return utcTime.format(DateTimeFormatter.ofPattern(outFormat));
    }

    private static Calendar initCalenderWithMillis(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        return cal;
    }
}