package com.power.common.util;

import java.util.*;
import java.util.regex.Matcher;
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

    /**
     * Extract strings
     *
     * @param pattern pattern
     * @param msg     msg
     * @return List of String
     */
    public static List<String> extractStrings(String pattern, String msg) {
        Matcher matcher = Pattern.compile(pattern).matcher(msg);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            int len = matcher.groupCount();
            for (int i = 1; i <= len; ++i) {
                String a = matcher.group(i);
                result.add(a);
            }
        }
        return result;
    }


}
