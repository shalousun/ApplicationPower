package com.power.common.builder;

import com.power.common.util.StringUtil;

import java.util.List;

/**
 * @author sunyu
 */
public class DubboInterfaceBuilder {

    /**
     * Generate dubbo provider service registration list
     *
     * @param classes list of class
     * @return dubbo provider interfaces
     */
    public static String generateDubboProviderInterface(List<Class> classes) {
        StringBuilder builder = new StringBuilder();
        for (Class clazz : classes) {
            builder.append("<dubbo:service interface=\"").append(clazz.getName());
            builder.append("\"").append(" ref=\"").append(StringUtil.firstToLowerCase(clazz.getSimpleName())).append("\"/>\n");
        }
        return builder.toString();
    }

    /**
     * Generate a registry list of Dubbo consumer services
     *
     * @param classes list of class
     * @return dubbo consumer references
     */
    public static String generateDubboConsumerInterface(List<Class> classes) {
        StringBuilder builder = new StringBuilder();
        for (Class clazz : classes) {
            builder.append("<dubbo:reference interface=\"").append(clazz.getName());
            builder.append("\"").append(" id=\"").append(StringUtil.firstToLowerCase(clazz.getSimpleName())).append("\"/>\n");
        }
        return builder.toString();
    }
}
