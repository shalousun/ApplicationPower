package com.power.common.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class RegexUtilTest {

    @Test
    public void testIsMatches() {
        Set<String> pattern = new TreeSet<>();
        pattern.add("com.yun:.*");
        String pk = "com.yun:a";
        System.out.println(RegexUtil.isMatches(pattern, pk));

        Optional<String> optional = Optional.empty();
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("没有数据");
        }
    }

    @Test
    public void testExtractMap() {
        String msg = "2022-08-01 23:00:53.603 [http-nio-8080-exec-9] ERROR com.benchmark.springboot.controller.LogController@testLog:28 - error log";
        String reg = "(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+\\.\\d+)\\s+\\[([^]]+)]\\s+(\\w+)\\s+(\\S+)\\s-\\s(.*)";
        System.out.println(ValidateUtil.validate(msg, reg));

        List<String> arr = RegexUtil.extractStrings(reg, msg);
        for (String str : arr) {
            System.out.println(str);
        }
    }

}
