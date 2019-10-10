package com.power.common.util;

import org.junit.Test;

/**
 * @author yu 2019/10/10.
 */
public class UrlUtilTest {

    @Test
    public void testSimplifyUrl() {
        String url = "http://192.168.1.11:8080/v1//questions/";

        System.out.println(UrlUtil.simplifyUrl(url));
    }
}
