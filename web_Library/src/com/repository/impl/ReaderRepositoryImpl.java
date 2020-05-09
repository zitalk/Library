package com.repository.impl;

import com.entity.Reader;
import com.repository.ReaderRepository;
import com.utils.JDBCTools;

import java.sql.*;

/**
 * RederRepository的实现类，相当于M层。
 */
public class ReaderRepositoryImpl implements ReaderRepository {
    @Override
    public Reader login(String username, String password) {
        Connection connection = JDBCTools.getConnection();
        String sql ="select * from reader where username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Reader reader = null;

        try {
            statement =connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                reader = new Reader(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);

        }

        return reader;
    }
}