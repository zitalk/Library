package com.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTools {
    /**
     * c3p0是一个数据库连接池，是对DataSource（接口）的实现，在这里定义成静态对象
     */
    private static DataSource dataSource;
    static {
        dataSource = new ComboPooledDataSource("testc3p0");//将c3p0-config.xml的名字传进来

    }

    public static Connection getConnection(){
       Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if (connection != null){
                connection.close();
            }
            if (statement != null){
                statement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
