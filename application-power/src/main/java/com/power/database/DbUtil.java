package com.power.database;

import java.sql.*;


/**
 * jdbc链接数据库的工具
 * @author sunyu
 */
public class DbUtil {
    private static DbProperties prop = new DbProperties();
    private static final String DRIVER = prop.getDriver();
    private static final String URL = prop.getUrl();
    private static final String USER = prop.getUsername();
    private static final String PASSWORD = prop.getPassword();

    /**
     * 获取数据
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 获取数据库元数据
     * @param connection
     * @return
     */
    public static DatabaseMetaData getDatabaseMetaData(Connection connection) {
        DatabaseMetaData databaseMetaData;
        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return databaseMetaData;
    }

    /**
     * 关闭链接
     * @param conn
     */
    public static void close(Connection conn){
        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭链接
     * @param conn
     * @param pstmt
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != pstmt) {
                pstmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

