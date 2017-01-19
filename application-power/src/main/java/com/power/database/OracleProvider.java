package com.power.database;


import com.power.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 针对oracle数据的信息提供者
 * @author sunyu 2016/12/11.
 */
public class OracleProvider implements DbProvider {
    @Override
    public Map<String, Column> getColumnsInfo(String tableName) {
        return null;
    }

    @Override
    public List<TableInfo> getTablesInfo(String tableName) {
        List<TableInfo> tableList;
        StringBuilder sql = new StringBuilder();
        sql.append("select * from USER_TAB_COMMENTS WHERE 1=1");
        if(StringUtils.isNotEmpty(tableName)){
            sql.append(" AND TABLE_NAME LIK ");
            sql.append("'%").append(tableName).append("%'");

        }
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TableInfo tableInfo;
        try{
            conn = DbUtil.getConnection();
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData() ;
            int columnCount = rsmd.getColumnCount();
            tableList = new ArrayList<>(columnCount);
            while(rs.next()){
                tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("TABLE_NAME").toLowerCase());
                tableInfo.setRemarks(rs.getString("COMMENTS"));
                tableList.add(tableInfo);
            }
        }catch(SQLException e){
            tableList = new ArrayList<>(0);
            e.printStackTrace();
        }finally{
           DbUtil.close(conn,stmt,rs);
        }
        return tableList;

    }
}
