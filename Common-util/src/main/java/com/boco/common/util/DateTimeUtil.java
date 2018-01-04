package com.boco.common.util;

/**
 * @author sunyu
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    private final static String[] WEEK_ARR = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static final String DATE_FORMAT_MINITE = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";

    public static final String DATE_FORMAT_CHINESE_SECONDE = "yyyy年MM月dd日 HH:mm:ss";

    public static final String DATE_FORMAT_CHINESE_WEEK_SECONDE =  "yyyy年MM月dd日 E HH:mm:ss";

    public static final long DAY_MS = 86400000L;


    /**
     * Formats a java.util.Date using a format string
     *
     * @param date   The date to format
     * @param format The format string like yyyy-MM-dd
     * @return String
     */
    public static String parseDateToStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Formats a java.sql.Date using a format string
     * @param date java.sql.Date
     * @param format format like yyyy-MM-dd
     * @return
     */
    public static String parseDateToStr(java.sql.Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Generation time,time format for yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */

    public static String getStrTimeDay() {
        Date date = new Date();
        return parseDateToStr(date,DATE_FORMAT_DAY);
    }

    /**
     * Generation sqlDate
     *
     * @return sqlDate
     */
    public static java.sql.Date getSqlDate() {
        Date utilDate = new Date();
        return new java.sql.Date(utilDate.getTime());
    }

    public static String sqlDateToStr(java.sql.Date date) {
        return parseDateToStr(date,DATE_FORMAT_DAY);
    }

    public static java.sql.Date strToSqlDate(String date) {
        Date utilDate = strToDate(date);
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * Parses a date using a specified format string
     *
     * @param sDate String
     * @param format String
     * @return Date
     */
    public static Date parseDate(String sDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * converts string type to java.util.Date type
     *
     * @param date String
     * @return java.util.Date
     */
    public static Date strToDate(String date) {
        return parseDate(date,DATE_FORMAT_DAY);
    }

    /**
     * Converts java.util.Date type to string type
     *
     * @param date java.util.Date
     * @return String
     */
    public static String dateToStr(Date date) {
        return parseDateToStr(date,DATE_FORMAT_DAY);
    }

    /**
     * Generation timestamp
     *
     * @return java.sql.Timestamp
     */
    public static Timestamp timeStamp() {
        return new Timestamp(getNowTime());
    }

    /**
     * format a string time like '2013-02-13' to '2013-02-13 00:00:00.0'
     *
     * @param date String
     * @return java.sql.Timestamp
     */
    public static Timestamp strToStamp(String date) {
       return strToStamp(date,DATE_FORMAT_DAY);
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static Timestamp strToStamp(String date,String format){
        Timestamp time = null;
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                time = new Timestamp(df.parse(date).getTime());
            } catch (Exception e) {

            }
        }
        return time;
    }

    public static Timestamp getTimestampFromStr(String date) {
        Timestamp time = null;
        try {
            time = Timestamp.valueOf(date);
        } catch (IllegalArgumentException e) {

        }
        return time;
    }

    /**
     * @return String
     */
    public static String getTime() {
        Date now = new Date();
        return parseDateToStr(now, "yyyy/MM/dd");
    }

    /**
     * input began time and end time calculate run time
     *
     * @param beginTime long
     * @param endTime long
     * @return double
     */
    public static double calcRunTime(long beginTime, long endTime) {
        return (endTime - beginTime) / 1000.00;
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
     *
     * @param before
     * @param after
     * @return
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
     * @param fmt String
     * @return String
     */
    public static String timestampToString(Timestamp time, String fmt) {
        SimpleDateFormat df = new SimpleDateFormat(fmt);
        return df.format(time);
    }

    /**
     * set time to next day's 0 hour 0 minute 0 second
     *
     * @param time java.sql.Timestamp
     * @return long
     */
    public static long setTimeToNextDay0H0M0S(Timestamp time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());
            cal.add(Calendar.DATE, 1);
            // 时、分、秒、毫秒置零
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // time.setTime(cal.getTimeInMillis());
            return cal.getTimeInMillis();
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * set time to next day's 0 hour 0 minute 0 second
     *
     * @param millis long
     * @return long
     */
    public static long setTimeToNextDay0H0M0S(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // time.setTime(cal.getTimeInMillis());
        return cal.getTimeInMillis();
    }

    /**
     * if the time is today return current milliseconds else set time to next
     * day's 0 hour 0 minute 0 second then return except today
     *
     * @param millis long
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
        if (stamp != null) {
            boolean flag = isDifferentDay(timeStamp(), stamp);
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
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // time.setTime(cal.getTimeInMillis());
            return cal.getTimeInMillis();
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
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // time.setTime(cal.getTimeInMillis());
        return cal.getTimeInMillis();

    }

    /**
     * set time to last day's 0 hour 0 minute 0 second
     *
     * @param time java.sql.Timestamp
     */
    public static void setTimeToLastDay0H0M0S(Timestamp time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());
            cal.add(Calendar.DATE, -1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            time.setTime(cal.getTimeInMillis());
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * 判断是否是同一天
     *
     * @param calendar1 Calendar
     * @param calendar2 Calendar
     * @return boolean
     */
    public static boolean isDifferentDay(Calendar calendar1, Calendar calendar2) {
        return (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR) || calendar1
                .get(Calendar.DAY_OF_YEAR) != calendar2
                .get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 判断是否是同一天
     *
     * @param timestamp0 java.sql.Timestamp
     * @param timestamp1 java.sql.Timestamp
     * @return boolean
     */
    public static boolean isDifferentDay(Timestamp timestamp0, Timestamp timestamp1) {
        if (timestamp0 == null || timestamp1 == null) {
            throw new NullPointerException("Timestamp can not be null");
        } else {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(timestamp0.getTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(timestamp1.getTime());
            return isDifferentDay(cal1, cal2);
        }
    }

    /**
     * 判断是否是同一天
     *
     * @param millis0 long
     * @param millis1 long
     * @return boolean
     */
    public static boolean isDifferentDay(long millis0, long millis1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(millis0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(millis1);
        return isDifferentDay(cal1, cal2);
    }

    /**
     * 将毫秒时间格式化为字符串时间(yyyy-MM-dd)
     *
     * @param millSec 毫秒数
     * @return String
     */
    public static String long2Str(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_CHINESE);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 将毫秒时间格式化为指定格式的字符串时间
     *
     * @param millSec 毫秒
     * @param format 需要格式的样式(yyyy-MM-dd等)
     * @return String
     */
    public static String long2Str(long millSec, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINESE);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 将毫秒时间格式化为指定格式的字符串时间
     * @param millSec
     *          毫秒
     * @param format
     *          需要格式的样式(yyyy-MM-dd等)
     * @param locale
     *          语言地域
     * @return
     */
    public static String long2Str(long millSec, String format,Locale locale){
        SimpleDateFormat sdf = new SimpleDateFormat(format,locale);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * transfer string to long
     *
     * @param dateFormat String
     * @param strDate String
     * @return long
     */
    public static long strToLong(String dateFormat, String strDate) {
        long temp;
        if (isEmpty(strDate)) {
            temp = 0;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            try {
                Date date = sdf.parse(strDate);
                temp = date.getTime();
            } catch (ParseException e) {
                temp = 0;
            }
        }
        return temp;
    }

    private static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim())
                || "null".equals(str.trim()) || "NaN".equals(str.trim());
    }

    /**
     *  根据当天时间戳获取从0点起经过的毫秒数
     * @param millions long
     * @return long
     */
    public static long todayPastMillisecond(long millions){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millions);
        return cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60;
    }

    /**
     * 获取本月天数
     *
     * @return int
     */
    public static int getCurrentMonthDays() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 根据时间获得所属月的天数
     *
     * @param stamp java.sql.Timestamp
     * @return int
     */
    public static int getCurrentMonthDays(Timestamp stamp) {
        if (null != stamp) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(stamp.getTime());
            return getCurrentMonthDays(cal);
        } else {
            throw new NullPointerException("Timestamp can not be null");
        }
    }

    /**
     * 根据long时间戳获取所属于的天数
     * @param ms
     * @return
     */
    public static int getCurrentMonthDays(long ms){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        return getCurrentMonthDays(cal);
    }
    /**
     *
     * @param cal
     * @return
     */
    public static int getCurrentMonthDays(Calendar cal){
        if(null != cal){
            cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
            cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
            int maxDate = cal.get(Calendar.DATE);
            return maxDate;
        }else{
            throw new NullPointerException("Calendar can not be null");
        }
    }

    /**
     * 根据当前时间获取所属周的第一天(0HOMOS) 根据中国习惯将星期一当做第一天
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
     * 根据时间获取所属周的第一天(0HOMOS) 根据中国习惯将星期一当做第一天
     *
     * @param ms long
     * @return long
     */
    public static long getFirstDayOfCurrentWeek(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            // 如果是星期天，则设置为上周
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        } else {
            // 如果不是星期天，则设置为本周
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        }
        return cal.getTimeInMillis();
    }


    /**
     * 将时间设置位当年第一天，并且将时分秒全部置0
     *
     * @param millis long
     * @return long
     */
    public static long setToFirstDayOfCurrentYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 将时间设置为下一年的第一天
     * @param millis long
     * @return long
     */

    public static long setToFirstDayOfNextYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.YEAR, 1);
        return cal.getTimeInMillis();
    }

    /**
     * 将时间重置到上月的第一天
     * @param ms
     * @return
     */
    public static long setToFirstDayOfLastMonth(long ms){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        cal.add(Calendar.MONTH,-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 上月同期
     * @param ms
     * @return
     */
    public static long setToLastMonthCommonDay(long ms){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTimeInMillis();
    }
    /**
     * 将时间重置为时间当前月的第一天，并且将时分秒全置0
     *
     * @param millis long
     * @return long
     */
    public static long setToFirstDayOfCurrentMonth(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 将时间重置为下月的第一天，并将时分秒全置0
     *
     * @param millis long
     * @return long
     */
    public static long setToFirstDayOfNextMonth(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 根据时间获取下一年的同一天
     *
     * @param millis long
     * @return long
     */
    public static long setToNextYearCommonDay(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        return cal.getTimeInMillis();
    }

    /**
     * 去年同期
     * @param millis
     * @return
     */
    public static long setToLastYearCommonDay(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据时间获取所属周的最后一天(中国习惯)
     *
     * @param stamp java.sql.Timestamp
     * @return long
     */
    public static long getLastDayOfCurrentWeek(Timestamp stamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(stamp.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTimeInMillis();
    }

    /**
     * 根据时间的得到所对应季度的第一天(0H0M0S)
     * @param ms
     *          毫秒数
     * @return long
     */
    public static long getFirstDayOfCurrentQuarter(long ms){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        int currentMonth = cal.get(Calendar.MONTH)+1;
        if(currentMonth >= 1 && currentMonth <= 3){
            cal.set(Calendar.MONTH, 0);
        }else if(currentMonth >= 4 && currentMonth <= 6){
            cal.set(Calendar.MONTH,3);
        }else if(currentMonth >= 7 && currentMonth <= 9){
            cal.set(Calendar.MONTH,6);
        }else if(currentMonth >= 10 && currentMonth <= 12){
            cal.set(Calendar.MONTH,9);
        }
        cal.set(Calendar.DATE,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 根据时间获取下一个季度的第一天(0H0M0S)
     * @param ms
     *          毫秒数
     * @return long
     */
    public static long getFirstDayOfNextQuarter(long ms){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        int currentMonth = cal.get(Calendar.MONTH)+1;
        if(currentMonth >= 1 && currentMonth <= 3){
            cal.set(Calendar.MONTH, 2);
            cal.set(Calendar.DATE,31);
        }else if(currentMonth >= 4 && currentMonth <= 6){
            cal.set(Calendar.MONTH,5);
            cal.set(Calendar.DATE,30);
        }else if(currentMonth >= 7 && currentMonth <= 9){
            cal.set(Calendar.MONTH,8);
            cal.set(Calendar.DATE, 30);
        }else if(currentMonth >= 10 && currentMonth <= 12){
            cal.set(Calendar.MONTH,11);
            cal.set(Calendar.DATE,31);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis()+86400000L;
    }

    /**
     * 根据时间获取是周几(中国化)
     *
     * @param ms long
     * @return int
     */
    public static int getDayOfWeek(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        int a = cal.get(Calendar.DAY_OF_WEEK);
        if (a >= 2) {
            return a - 1;
        } else {
            return 7;
        }
    }

    /**
     * 创建一个时间并将时分秒都置0
     *
     * @return long
     */
    public static long create0H0M0STime() {
        return setTimeTo0H0M0S(System.currentTimeMillis());
    }

    /**
     * 判断是否是今天
     *
     * @param ms 毫秒数
     * @return boolean
     */
    public static boolean isToday(long ms) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(ms);
        return !isDifferentDay(cal1, cal2);
    }

    /**
     * 将时间戳转换称友好的时间显示
     *
     * @param ms long
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
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        int a = cal.get(Calendar.DAY_OF_WEEK);
        String[] weekARR = {"（周日）", "（周一）", "（周二）", "（周三）", "（周四）", "（周五）", "（周六）"};
        return long2Str(ms, "MM月dd日" + weekARR[a - 1]);
    }

    public static String getDateWithWeekAndTime(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        int a = cal.get(Calendar.DAY_OF_WEEK);
        return long2Str(ms, "yyyy年MM月dd日 " + WEEK_ARR[a - 1] + " HH:mm");
    }

    /**
     * calc age
     *
     * @param ms long
     * @return int
     */
    public static int getAge(long ms) {
        int age;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        born.setTimeInMillis(ms);
        if (born.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }

    /**
     * 去年同期
     * @param strDate
     * @param format time format
     * @return
     */
    public static String getLastYearCommonDay(String strDate,String format){
        Date date = parseDate(strDate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) -1);
        return long2Str(calendar.getTimeInMillis(),format);
    }

    /**
     * 上月同期,如果是最后一天则重置到月末
     * @param strDate strDate
     * @param format time format
     * @return
     */
    public static String getLastMonthCommonDay(String strDate,String format){
        Date date = parseDate(strDate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int a = calendar.get(Calendar.DATE);
        int b = getCurrentMonthDays(date.getTime());
        if(a==b){
            long ms = setToFirstDayOfCurrentMonth(date.getTime())- DAY_MS;
            return long2Str(ms,format);
        }
        calendar.add(Calendar.MONTH,-1);
        return long2Str(calendar.getTimeInMillis(),format);
    }

    /**
     * get birthday from id card
     * @param idCard idCard
     * @return Timestamp
     */
    public static Timestamp getBirthdayFormIdCard(String idCard){
        Timestamp timestamp;
        if(ValidateUtil.isIdCard(idCard)){
            if(idCard.length()==18){
                timestamp = strToStamp(idCard.substring(6,14),"yyyyMMdd");
            }else{
                timestamp = strToStamp(idCard.substring(6,12),"yyyyMMdd");
            }
        }else{
            throw new RuntimeException("invalid IdChard number:"+idCard);
        }
        return timestamp;
    }

    /**
     *
     * @param idCard
     * @return
     */
    public static long getLongBirthFormIdCard(String idCard){
        return getBirthdayFormIdCard(idCard).getTime();
    }

    /**
     * 判断是否是今年
     * @param ms
     * @return
     */
    public static boolean isCurrentYear(long ms){
        Calendar calTemp = Calendar.getInstance();
        calTemp.setTimeInMillis(ms);
        int yearTemp = calTemp.get(Calendar.YEAR);
        Calendar calNow = Calendar.getInstance();
        int yearNow = calNow.get(Calendar.YEAR);
        if(yearNow == yearTemp){
            return true;
        }
        return false;
    }
}