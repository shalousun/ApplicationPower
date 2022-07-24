package com.power.common.util;

/**
 * @author yu 2021/5/22.
 */
public class JsonFormatUtilTest {
    public static void main(String[] args) {
        String str = "{\"name\":\"[\\\"a\\\",\\\"b\\\"]\",\"age\":\"a,b\"}";
        System.out.println(JsonFormatUtil.formatJson(str));
    }
}
