package com.power.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author yu 2020/4/29.
 */
public class RegexUtil {

    /**
     * match
     *
     * @param patterns Set of patterns
     * @param str      string
     * @return true if match
     */
    public static boolean isMatches(Collection<String> patterns, String str) {
        if (Objects.isNull(patterns)) {
            return false;
        }
        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            if (pattern.matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * match str
     *
     * @param patterns Set of patterns
     * @param str      string
     * @return true if match
     */
    public static boolean isMatches(String patterns, String str) {
        if (StringUtil.isNotEmpty(patterns)) {
            List<String> patternList = Arrays.asList(patterns.split(";", 0));
            return isMatches(patternList, str);
        } else {
            return false;
        }
    }
}
