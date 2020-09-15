package com.power.generator.database;

import com.power.common.util.StringUtil;
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
     * 获取表信息
     * @param tableName 表名
     * @return TableInfo
     */
    @Override
    public TableInfo getTableInfo(String tableName) {
        String primaryKey = getPrimaryKeysInfo(tableName);
        TableInfo tableInfo = new TableInfo();
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
                    tableInfo.setPrimaryKeyIsAutoIncrement(true);
                    column.setAutoIncrement(true);
                }
                if (columnName.equals(primaryKey)) {
                    tableInfo.setPrimaryKey(columnName);
                    tableInfo.setPrimaryKeyType(columnType);
                    column.setPrimaryKey(true);
                }
                colMap.put(columnName, column);
            }
            tableInfo.setColumnsInfo(colMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(connection);
        }
        return tableInfo;
    }

    /**
     * 获取主键字段
     *
     * @param tName 表名
     * @return 主键信息
     */
    public String getPrimaryKeysInfo(String tName) {
        String columnName = "";
        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            /**
             * 获取对给定表的主键列的描述
             * 方法原型:ResultSet getPrimaryKeys(String catalog,String schema,String table);
             * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
             * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
             * table - 表名称;可包含单字符通配符("_"),或多字符通配符("%");
             */
            rs = dbmd.getPrimaryKeys(conn.getCatalog(), null, tName);

            while (rs.next()) {
                String tableCat = rs.getString("TABLE_CAT"); //表类别(可为null)
                String tableSchemaName = rs.getString("TABLE_SCHEM");//表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
                String tableName = rs.getString("TABLE_NAME"); //表名
                columnName = rs.getString("COLUMN_NAME");//列名
                short keySeq = rs.getShort("KEY_SEQ");//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
                String pkName = rs.getString("PK_NAME"); //主键名称
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
        }
        return columnName;
    }

    @Override
    public List<TableInfo> getTablesInfo(String tableName, String filter) {
        List<TableInfo> tableList;
        StringBuilder sql = new StringBuilder();
        sql.append("show table status where ENGINE IS NOT NULL ");
        if (StringUtil.isNotEmpty(tableName)) {
            sql.append(" and NAME LIKE '%").append(tableName).append("%'");
        } else if (StringUtil.isNotEmpty(filter)) {
            sql.append(" and NAME LIKE '").append(filter).append("%'");
        }
        LOGGER.debug("MySQL provider sql: {}", sql.toString());
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TableInfo tableInfo;
        try {
            connection = DbUtil.getConnection();
            stmt = connection.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            tableList = new ArrayList<>();
            String comment;
            while (rs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("Name"));
                comment = rs.getString("Comment");
                if (StringUtil.isEmpty(comment)) {
                    tableInfo.setRemarks(rs.getString("Name"));
                } else {
                    tableInfo.setRemarks(rs.getString("Name") + "[" + comment + "]");
                }
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