package com.power.generator.database;

import com.boco.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对mysql数据库信息提供者
 *
 * @author sunyu 2016/12/11.
 */
public class MySqlProvider implements DbProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlProvider.class);
    /**
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, Column> getColumnsInfo(String tableName) {
        Map<String, Column> colMap = new LinkedHashMap<>();
        Connection connection = null;
        try {
            connection = DbUtil.getConnection();
            DatabaseMetaData meta = DbUtil.getDatabaseMetaData(connection);
            ResultSet colRet = meta.getColumns(connection.getCatalog(), "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                String isAutoIncrement = colRet.getString("IS_AUTOINCREMENT");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");
                String remarks = colRet.getString("REMARKS");
                String columnType = TypeConvert.sqlTypeToJavaType(dataType, digits);
                //设置列信息
                Column column = new Column();
                column.setColumnName(columnName);
                column.setColumnType(columnType);
                column.setRemarks(remarks);
                if ("YES".equals(isAutoIncrement)) {
                    column.setAutoIncrement(true);
                }
                colMap.put(columnName, column);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(connection);
        }
        return colMap;
    }

    @Override
    public List<TableInfo> getTablesInfo(String tableName,String filter) {
        List<TableInfo> tableList;
        StringBuilder sql = new StringBuilder();
        sql.append("show table status where ENGINE IS NOT NULL ");
        if (StringUtil.isNotEmpty(tableName)) {
            sql.append(" and NAME LIKE '%").append(tableName).append("%'");
        } else if(StringUtil.isNotEmpty(filter)){
            sql.append(" and NAME LIKE '").append(filter).append("%'");
        }
        LOGGER.debug("MySQL provider sql: {}",sql.toString());
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TableInfo tableInfo;
        try {
            connection = DbUtil.getConnection();
            stmt = connection.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            tableList = new ArrayList<>();
            while (rs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("Name"));
                tableInfo.setRemarks(rs.getString("Comment"));
                tableList.add(tableInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(connection, stmt, rs);
        }
        return tableList;
    }
}
