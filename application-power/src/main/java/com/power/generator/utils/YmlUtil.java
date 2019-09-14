package com.power.generator.utils;

import com.power.common.util.StringUtil;

/**
 * handle Special characters in yml
 *
 * @author yu 2018/06/04
 */
public class YmlUtil {


    /**
     * process special characters
     *
     * @param resource
     * @return
     */
    public static String addDoubleQuote(String resource) {
        if (StringUtil.isEmpty(resource)) {
            return resource;
        } else {
            if (resource.contains("!") || resource.contains("#")) {
                StringBuilder builder = new StringBuilder();
                builder.append("\"").append(resource).append("\"");
                return builder.toString();
            } else {
                return resource;
            }
        }
    }
}
