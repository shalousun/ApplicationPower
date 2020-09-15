package com.power.generator.factory;

import com.power.generator.database.DbProperties;
import com.power.generator.database.DbProvider;
import com.power.generator.database.MySqlProvider;
import com.power.generator.database.OracleProvider;
import com.power.generator.utils.GeneratorProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunyu 2016/12/11.
 */
public class DbProviderFactory {

    private final static List<String> drivers = new ArrayList<>();

    static {
        drivers.add("com.mysql.jdbc.Driver");
        drivers.add("com.mysql.cj.jdbc.Driver");
        drivers.add("oracle.jdbc.OracleDriver");
    }

    /**
     * 数据库属性
     */
    private final DbProperties properties;

    public DbProviderFactory() {
        properties = new DbProperties();
    }

    public DbProvider getInstance() {
        if(!GeneratorProperties.useDb()){
            return null;
        }
        String driverName = this.properties.getDriver();
        if (!drivers.contains(driverName)) {
            throw new RuntimeException("Can't support your db driver name.");
        }
        DbProvider provider;
        if ("com.mysql.jdbc.Driver".equals(driverName)) {
            provider = new MySqlProvider();
            return provider;
        }
        if ("com.mysql.cj.jdbc.Driver".equals(driverName)) {
            provider = new MySqlProvider();
            return provider;
        }
        if ("oracle.jdbc.OracleDriver".equals(driverName)) {
            provider = new OracleProvider();
            return provider;
        }
        return null;
    }
}
