package com.power.common.util;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author yu 2020/4/29.
 */
public class RegexUtil {

    /**
     * match artifact
     * @param patterns Set of patterns
     * @param str string
     * @return true if match
     */
    public static boolean isMatches(Set<String> patterns, String str) {
        if (null == patterns) {
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
}
