package com.power.generator.utils;

import java.util.Properties;

/**
 * 属性文件读取工具
 *
 * @author sunyu 2016/12/5.
 */
public class PropertiesUtils {
    private static final Properties props = new Properties();

    public PropertiesUtils(String source) {
        try {
            /**
             * properties
             */
            props.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(source));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
