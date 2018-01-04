package com.boco.common.builder;

import com.boco.common.util.StringUtil;

import java.util.List;

/**
 * @author sunyu
 *
 */
public class DubboInterfaceBuilder {

    /**
     * 生成dubbo提供方服务注册列表
     * @param classes
     */
    public static String generateDubboProviderInterface(List<Class> classes){
        StringBuilder builder = new StringBuilder();
        for(Class clazz:classes){
            builder.append("<dubbo:service interface=\"").append(clazz.getName());
            builder.append("\"").append(" ref=\"").append(StringUtil.firstToLowerCase(clazz.getSimpleName())).append("\"/>\n");
        }
        return builder.toString();
    }

    /**
     * 生成dubbo消费方服务注册列表
     * @param classes
     */
    public static String generateDubboConsumerInterface(List<Class> classes){
        StringBuilder builder = new StringBuilder();
        for(Class clazz:classes){
            builder.append("<dubbo:reference interface=\"").append(clazz.getName());
            builder.append("\"").append(" id=\"").append(StringUtil.firstToLowerCase(clazz.getSimpleName())).append("\"/>\n");
        }
        return builder.toString();
    }
}
