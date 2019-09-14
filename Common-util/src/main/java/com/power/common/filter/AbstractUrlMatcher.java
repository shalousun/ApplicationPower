package com.power.common.filter;

import java.util.Set;
import java.util.regex.Pattern;

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
        for (String str : patterns) {
            if (str.endsWith("/*")) {
                String name = str.substring(0, str.length() - 2);
                if (url.contains(name)) {
                    return true;
                }
            } else {
                Pattern pattern = Pattern.compile(str);
                if (pattern.matcher(url).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
