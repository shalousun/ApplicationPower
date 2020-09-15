package com.power.generator.database;

/**
 * @author sunyu on 2016/12/6.
 */
public class Column {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 列注释
     */
    private String remarks;

    /**
     * 是否主键
     */
    private boolean isPrimaryKey;

    /**
     * 是否自增
     *
     * @return
     */
    private boolean isAutoIncrement;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }
}
