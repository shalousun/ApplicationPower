package com.power.generator.database;

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
}
