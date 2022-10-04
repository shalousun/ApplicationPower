package com.power.common.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author yu 2022/10/4.
 */
public class ReflectionUtilTest {

    @Test
    public void testGetFinalFieldValue() throws IllegalAccessException {
        Map<String,String> map = ReflectionUtil.getFinalFieldValue(DateTimeUtil.class);
        for (Map.Entry<String,String> entry:map.entrySet()) {
            System.out.println(StringUtil.fillStringByArgs("key={0},value={1}", entry.getKey(),entry.getValue()));
        }
    }
}
