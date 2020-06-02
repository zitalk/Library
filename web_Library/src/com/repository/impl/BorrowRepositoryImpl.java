package com.repository.impl;

import com.repository.BorrowRepository;
import com.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorrowRepositoryImpl implements BorrowRepository {

    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCTools.getConnection();
        String  sql = "insert into borrow(bookid,readerid,borrowtime,returntime,state) values(?,?,?,?,0)";
        PreparedStatement preparedStatement = null;
        try {
            //替换参数
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4,returntime);
            preparedStatement.executeUpdate();//插入之后的更新
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCTools.release(connection,preparedStatement,null);
        }
    }
}
