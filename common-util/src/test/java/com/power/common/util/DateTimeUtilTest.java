package com.power.common.util;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;


public class DateTimeUtilTest {

    @Test
    public void testGetBirthdayFormIdCard() {
        DateTimeUtil.strToDate("2017-07-22", "yyyy-MM-dd");
        System.out.println(DateTimeUtil.getNowTime());
    }

    @Test
    public void testGetAge() {
        long ms = DateTimeUtil.strToLong("2017-07-09", "yyyy-MM-dd");
        int age = DateTimeUtil.getAge(ms);
        System.out.println("age: " + age);
    }

    @Test
    public void testUTCToCST() {
        String UTC = "2022-07-06T11:56:55.102Z";
        System.out.println(DateTimeUtil.UTCToCST(UTC, DateTimeUtil.DATE_FORMAT_MILLISECOND));
    }

    @Test
    public void testGetCurrentMonthDays() {
        System.out.println(DateTimeUtil.getLengthOfMonth(System.currentTimeMillis()));
    }

    @Test
    public void testLongToLocalDate() {
        System.out.println(DateTimeUtil.longToLocalDate(System.currentTimeMillis()));
    }

    @Test
    public void testLongToLocalDateTime() {
        System.out.println(DateTimeUtil.localDateTimeToStr(DateTimeUtil.longToLocalDateTime(System.currentTimeMillis()), DateTimeUtil.DATE_FORMAT_MILLISECOND));
    }

    @Test
    public void testIsToday() {
        System.out.println(DateTimeUtil.isToday(System.currentTimeMillis()));
    }

    @Test
    public void testGetDayOfWeek() {
        System.out.println(DateTimeUtil.getDayOfWeek(System.currentTimeMillis()));
    }

    @Test
    public void testSetTimeToNextDay0H0M0S() {
        long ms = DateTimeUtil.setTimeToNextDay0H0M0S(new Timestamp(System.currentTimeMillis()));
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetTimeTo0H0M0S() {
        long ms = DateTimeUtil.setTimeTo0H0M0S(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToFirstDayOfCurrentMonth() {
        long ms = DateTimeUtil.setToFirstDayOfCurrentMonth(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToFirstDayOfNextMonth() {
        long ms = DateTimeUtil.setToFirstDayOfNextMonth(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToFirstDayOfLastMonth() {
        long ms = DateTimeUtil.setToFirstDayOfLastMonth(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToLastMonthCommonDay() {
        long ms = DateTimeUtil.setToLastMonthCommonDay(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToNextYearCommonDay() {
        long ms = DateTimeUtil.setToNextYearCommonDay(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToLastYearCommonDay() {
        long ms = DateTimeUtil.setToLastYearCommonDay(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetToFirstDayOfNextYear() {
        long ms = DateTimeUtil.setToFirstDayOfNextYear(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testSetSetToFirstDayOfCurrentYear() {
        long ms = DateTimeUtil.setToFirstDayOfCurrentYear(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testGetFirstDayOfCurrentWeek() {
        long ms = DateTimeUtil.getFirstDayOfCurrentWeek(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testGetLastDayOfCurrentWeek() {
        long ms = DateTimeUtil.getLastDayOfCurrentWeek(new Timestamp(System.currentTimeMillis()));
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }

    @Test
    public void testGetFirstDayOfCurrentQuarter() {
        long ms = DateTimeUtil.getFirstDayOfNextQuarter(System.currentTimeMillis());
        System.out.println(DateTimeUtil.long2Str(ms, DateTimeUtil.DATE_FORMAT_SECOND));
    }


}
