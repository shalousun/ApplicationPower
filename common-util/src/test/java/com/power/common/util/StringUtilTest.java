package com.power.common.util;

import org.junit.jupiter.api.Test;

/**
 * Created by yu on 2017/2/9.
 */
public class StringUtilTest {

    @Test
    public void testFormat() {
        String msg = StringUtil.format("hello {},date={},number={}","world",DateTimeUtil.nowStrTime(),2);
        System.out.println(msg);
    }

    @Test
    public void testIsIdCard() {
        String str = "36032319911018276X";
        System.out.println(ValidateUtil.isIdCard(str));
    }
}
