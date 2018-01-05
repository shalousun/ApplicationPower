package com.power.generator.database;

import java.util.Properties;

/**
 * @author sunyu
 */
public class DbProperties {

    private static final Properties props = new Properties();

    public DbProperties() {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DbProperties(String source) {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(source));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return props.getProperty("jdbc.username");
    }

    public String getPassword() {
        return props.getProperty("jdbc.password");
    }

    public String getDriver() {
        return props.getProperty("jdbc.driver");
    }

    public String getUrl() {
        return props.getProperty("jdbc.url");
    }
}
