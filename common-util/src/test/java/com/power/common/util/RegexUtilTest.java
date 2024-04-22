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
        String msg = "2024-04-19T21:42:07.307+08:00  INFO 23801 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]";
        String reg = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\+\\d{2}:\\d{2})\\s+(\\w+)\\s+\\d+\\s+---\\s+\\[(.*?)\\]\\s+(.*?)\\s+:\\s+(.*)";
        System.out.println(ValidateUtil.validate(msg, reg));

        List<String> arr = RegexUtil.extractStrings(reg, msg);
        for (String str : arr) {
            System.out.println(str);
        }
    }

}
