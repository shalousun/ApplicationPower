package com.boco.common.util;

import org.junit.Test;

public class DateTimeUtilTest {

    @Test
    public void testGetBirthdayFormIdCard(){
        String idCard = "530629199108120750";
        System.out.println(DateTimeUtil.timestampToString(DateTimeUtil.getBirthdayFormIdCard(idCard),DateTimeUtil.DATE_FORMAT_DAY));
    }
}
