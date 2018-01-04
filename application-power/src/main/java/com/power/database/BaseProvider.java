package com.power.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/3/10.
 */
public class BaseProvider {
    /**
     * 获取表名
     *
     * @param tableName
     * @return
     */
    public List<TableInfo> getTablesInfo(String tableName) {
        List<TableInfo> tableInfos = new ArrayList<>();
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;
        String[] types = {"TABLE"};
        try {
            conn = DbUtil.getConnection();
            dbmd = DbUtil.getDatabaseMetaData(conn);
            rs = dbmd.getTables(null, null, "%", types);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("TABLE_NAME"));
                tableInfo.setName(rs.getString("REMARKS"));
                tableInfos.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn, rs);
        }
        return tableInfos;
    }
}
