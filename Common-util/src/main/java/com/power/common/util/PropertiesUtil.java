package com.power.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author sunyu
 */
public class PropertiesUtil {

    /**
     * 从文件读取属性文件
     *
     * @param filename file name
     * @return properties
     * @throws IOException io exception
     */
    public static Properties loadFromFile(String filename) throws IOException {
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filename)) {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        }
    }
}
