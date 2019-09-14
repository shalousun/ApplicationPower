package com.power.generator.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yu on 2017/3/10.
 */
public class BaseProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProvider.class);

    /**
     * 获取字段信息,DatabaseMetaData.getColumns无法获取oracle的字段注释，
     * 因此调用次方法
     *
     * @param sql
     * @return
     */
    protected Map<String, Column> getColumnsSchema(String sql) {
        Map<String, Column> colMap = new LinkedHashMap<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet colRet = null;
        try {
            connection = DbUtil.getConnection();
            pst = connection.prepareStatement(sql);
            colRet = pst.executeQuery();
            while (colRet.next()) {
                String columnName = colRet.getString("column_name").toLowerCase();
                String remarks = colRet.getString("comments");
                String columnType = colRet.getString("data_type").toLowerCase();
                //设置列信息
                Column column = new Column();
                column.setColumnName(columnName);
                column.setRemarks(remarks);
                column.setColumnType(TypeConvert.sqlTypeToJavaType(columnType));
                colMap.put(columnName, column);
            }
        } catch (Exception e) {
            LOGGER.error("读取schema信息失败", e);
        } finally {
            DbUtil.close(connection, pst, colRet);
        }
        return colMap;
    }

    /**
     * 获取表信息
     *
     * @param sql
     * @param ds
     * @return
     */
    protected List<TableInfo> getTablesSchema(String sql) {
        List<TableInfo> tableList;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TableInfo tableInfo;
        try {
            connection = DbUtil.getConnection();
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            tableList = new ArrayList<>();
            while (rs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("TABLE_NAME").toLowerCase());
                tableInfo.setRemarks(rs.getString("comments"));
                tableList.add(tableInfo);
            }
        } catch (SQLException e) {
            LOGGER.error("读取字段信息失败", e);
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(connection, stmt, rs);
        }
        return tableList;
    }
}
