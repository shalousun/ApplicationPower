package com.power.datasource.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by yu on 2017/3/21.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 取得当前使用那个数据源。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDatasourceType();
    }

}
