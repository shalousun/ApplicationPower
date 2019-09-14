package com.power.generator.database;


import com.power.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 针对oracle数据的信息提供者
 *
 * @author sunyu 2016/12/11.
 */
public class OracleProvider extends BaseProvider implements DbProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(OracleProvider.class);

    @Override
    public TableInfo getTableInfo(String tableName) {
        String sql = "SELECT COL.table_name, col.column_name, col.data_type, c.comments , CASE uc.constraint_type " +
                "WHEN 'P' THEN 'yes' ELSE '' END AS \"PRIMARY_KEY\" FROM user_tab_columns col LEFT JOIN " +
                "user_cons_columns ucc ON ucc.table_name = col.table_name AND ucc.column_name = col.column_name LEFT JOIN " +
                "user_constraints uc ON uc.constraint_name = ucc.constraint_name AND uc.constraint_type = 'P' LEFT JOIN " +
                "user_col_comments c ON col.table_name = c.table_name and col.column_name = c.column_name";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(sql);
        if (StringUtil.isNotEmpty(tableName)) {
            sqlBuilder.append(" where col.table_name = '").append(tableName.toUpperCase()).append("'");
        }
        LOGGER.debug("oracle provider sql: {}", sqlBuilder.toString());
        TableInfo tableInfo = new TableInfo();
        tableInfo.setColumnsInfo(getColumnsSchema(sqlBuilder.toString()));
        tableInfo.setPrimaryKeyType("long");
        return tableInfo;
    }

    @Override
    public List<TableInfo> getTablesInfo(String tableName, String filter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name,comments from USER_TAB_COMMENTS WHERE 1=1");
        if (StringUtil.isNotEmpty(tableName)) {
            sql.append(" AND TABLE_NAME LIKE ");
            sql.append("'%").append(tableName).append("%'");
        } else if (StringUtil.isNotEmpty(filter)) {
            sql.append(" AND TABLE_NAME LIKE ");
            sql.append("'").append(filter).append("%'");
        }
        LOGGER.debug("Query table sql: {}", sql.toString());
        return getTablesSchema(sql.toString());
    }
}
