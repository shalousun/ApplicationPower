package com.power.common.util;

import org.junit.jupiter.api.Test;


/**
 * @author yu 2021/6/27.
 */
public class PathMatcherTest {

    @Test
    public void testMatch() {
        String pattern = "/test*";
        String url = "/test1";
        PathMatcher matcher = new PathMatcher();
        System.out.println(matcher.match(pattern, url));
    }
}
