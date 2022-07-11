package com.power.common.filter;

import com.power.common.util.PathMatcher;

import java.util.Set;

public abstract class AbstractUrlMatcher {

    /**
     * Returns true if matched, false if not matched
     *
     * @param patterns Regular expression or wildcard
     * @param url      url
     * @return boolean
     */
    protected boolean isMatches(Set<String> patterns, String url) {
        if (null == patterns) {
            return false;
        }
        PathMatcher matcher = new PathMatcher();
        for (String str : patterns) {
            if (matcher.matches(str, url)) {
                return true;
            }
        }
        return false;
    }
}
