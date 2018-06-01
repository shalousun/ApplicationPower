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
     */
    private boolean isAutoIncrement;
    /**
     * 长度
     * add fujunsu 2018-3-29 14:12:13
     */
    private int dataSize;
    /**
     * 是否可空
     * add fujunsu 2018年3月29日14:12:29
     */
    private boolean nullable;

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

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int size) {
        this.dataSize = size;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
