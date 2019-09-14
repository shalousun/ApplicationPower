package com.power.generator.database;

import java.util.List;

/**
 * Created by yu on 2016/12/11.
 */
public interface DbProvider {
    /**
     * 根据表名获取表信息，包括主键，表全部字段信息
     *
     * @param tableName
     * @return
     */
    TableInfo getTableInfo(String tableName);

    /**
     * 获取所有表信息
     *
     * @param tableName 表名
     * @param filter    表名过滤器
     * @return
     */
    List<TableInfo> getTablesInfo(String tableName, String filter);
}
