package com.power.generator.factory;

import com.power.generator.database.DbProperties;
import com.power.generator.database.DbProvider;
import com.power.generator.database.MySqlProvider;
import com.power.generator.database.OracleProvider;

/**
 * @author sunyu 2016/12/11.
 */
public class DbProviderFactory {
    /**
     * 数据库属性
     */
    private DbProperties properties;

    public DbProviderFactory() {
        properties = new DbProperties();
    }

    public DbProvider getInstance() {
        String driverName = this.properties.getDriver();
        DbProvider provider = null;
        if ("com.mysql.jdbc.Driver".equals(driverName)) {
            provider = new MySqlProvider();
        }
        if ("com.mysql.cj.jdbc.Driver".equals(driverName)) {
            provider = new MySqlProvider();
        }
        if ("oracle.jdbc.driver.OracleDriver".equals(driverName)) {
            provider = new OracleProvider();
        }
        return provider;
    }
}
