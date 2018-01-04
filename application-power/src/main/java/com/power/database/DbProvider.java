package com.power.database;

import java.util.List;
import java.util.Map;

/**
 * Created by yu on 2016/12/11.
 */
public interface DbProvider {
    /**
     * 根据表名获取列信息
     *
     * @param tableName
     * @return
     */
    Map<String, Column> getColumnsInfo(String tableName);

    /**
     * 获取所有表信息
     *
     * @return
     */
    List<TableInfo> getTablesInfo(String tableName);
}
