package com.power.generator.database;

import com.alibaba.fastjson.JSON;
import org.beetl.ext.fn.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
     * 获取字段信息
     * @param sql
     * @return
     */
    protected Map<String, Column> getColumnsSchema(String sql){
        Map<String, Column> colMap = new LinkedHashMap<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet colRet = null;
        try {
            connection = DbUtil.getConnection();
            pst = connection.prepareStatement(sql);
            colRet = pst.executeQuery();
            while (colRet.next()) {
                String columnName = colRet.getString("column_name");
                String remarks = colRet.getString("comments");
                //设置列信息
                Column column = new Column();
                column.setColumnName(columnName);
                column.setRemarks(remarks);
                colMap.put(columnName, column);
            }
        } catch (Exception e) {
            LOGGER.error("读取schema信息失败",e);
        } finally {
            DbUtil.close(connection,pst,colRet);
        }
        return colMap;
    }

    /**
     * 获取表信息
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
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            tableList = new ArrayList<>(columnCount);
            while (rs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setName(rs.getString("name"));
                tableInfo.setRemarks(rs.getString("comments"));
                tableList.add(tableInfo);
            }
        } catch (SQLException e) {
            LOGGER.error("读取字段信息失败",e);
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(connection, stmt, rs);
        }
        return tableList;
    }

    public static void main(String[] args) {
        Map<String, Column> list = new OracleProvider().getColumnsInfo("TB_HZJL");
        System.out.println("json:"+JSON.toJSONString(list));
    }
}
