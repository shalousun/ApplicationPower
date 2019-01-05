package com.power.generator.database;

import java.util.Map;

/**
 * 数据库表信息
 *
 * @author sunyu 2016/12/11.
 */
public class TableInfo {
    /**
     * 表名
     */
    private String name;
    /**
     * 表注释
     */
    private String remarks;

    /**
     * 表主键字段名
     */
    private String primaryKey;

    /**
     * 表主键字段类型
     */
    private String primaryKeyType;

    /**
     * 主键是否自增
     */
    private boolean primaryKeyIsAutoIncrement;

    /**
     * 表字段信息
     */
    private Map<String, Column> columnsInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public Map<String, Column> getColumnsInfo() {
        return columnsInfo;
    }

    public void setColumnsInfo(Map<String, Column> columnsInfo) {
        this.columnsInfo = columnsInfo;
    }

    public boolean isPrimaryKeyIsAutoIncrement() {
        return primaryKeyIsAutoIncrement;
    }

    public void setPrimaryKeyIsAutoIncrement(boolean primaryKeyIsAutoIncrement) {
        this.primaryKeyIsAutoIncrement = primaryKeyIsAutoIncrement;
    }
}
