package com.power.common.filter;

import com.power.common.util.PathMatcher;

import java.util.Set;

/**
 * 抽象的url匹配封装
 */
public abstract class AbstractUrlMatcher {

    /**
     * 匹配返回true，不匹配返回false
     *
     * @param patterns 正则表达式或通配符
     * @param url      请求的url
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
