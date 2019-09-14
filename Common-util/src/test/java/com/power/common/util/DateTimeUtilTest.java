package com.power.common.util;

import org.junit.Test;


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


}
