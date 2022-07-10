package com.power.common.util;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.junit.jupiter.api.Test;


public class DateTimeUtilTest {

    @Test
    public void testGetBirthdayFormIdCard() {
        DateTimeUtil.strToDate("2017-07-22", "yyyy-MM-dd");
        System.out.println(DateTimeUtil.getNowTime());
    }

    @Test
    public void testGetAge() {
        int age = DateTimeUtil.getAge(System.currentTimeMillis() + 100);
        System.out.println("age: " + age);
    }

    @Test
    public void testUTCToCST(){
        String UTC = "2022-07-06T11:56:55.102Z";
        System.out.println(DateTimeUtil.UTCToCST(UTC,DateTimeUtil.DATE_FORMAT_MILLISECOND));
    }
    @Test
    public void testGetCurrentMonthDays(){
        System.out.println(DateTimeUtil.getCurrentMonthDays(Calendar.getInstance()));
    }
    @Test
    public void testLongToLocalDate() {
        System.out.println(DateTimeUtil.longToLocalDate(System.currentTimeMillis()));
    }

    @Test
    public void testLongToLocalDateTime() {
        System.out.println(DateTimeUtil.localDateTimeToStr(DateTimeUtil.longToLocalDateTime(System.currentTimeMillis()),DateTimeUtil.DATE_FORMAT_MILLISECOND));
    }

    @Test
    public void testIsToday() {
        System.out.println(DateTimeUtil.isToday(System.currentTimeMillis()));
    }

    @Test
    public void testGetDayOfWeek(){
        System.out.println(DateTimeUtil.getDayOfWeek(System.currentTimeMillis()));
    }


}
