package com.repository.impl;

import com.entity.Book;
import com.entity.BookCase;
import com.repository.BookRepository;
import com.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BookRepository实现类
 */
public class BookRepositoryImpl implements BookRepository {
    /**
     * 从数据库中指定位置取出指定本书
     *
     * @param index
     * @param limit
     * @return
     */
    @Override
    public List<Book> findAll(int index, int limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select * from book,bookcase where book.bookcaseid = bookcase.id limit ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, index);//替换第一个参数为index
            preparedStatement.setInt(2, limit);//替换第二个参数为limit
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 /*
                直接将代码封进add里，减小栈内存开销
                BookCase bookCase = new BookCase(resultSet.getInt("9"),resultSet.getString("10"));
                Book book =new Book(resultSet.getInt("1"),resultSet.getString("2"),resultSet.getString("3"),resultSet.getString("4"),resultSet.getInt("5"),resultSet.getDouble("6"),bookCase);
                list.add(book);
                 */
                list.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getDouble(6), new BookCase(resultSet.getInt(9), resultSet.getString(10))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return list;
    }

    /**
     * 获得到所有图书的条数，用于计算总页数，右下角展示。
     *
     * @return
     */
    @Override
    public int count() {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) from book,bookcase where book.bookcaseid = bookcase.id";//丛数据库计数获得总本数
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return count;
    }
}
