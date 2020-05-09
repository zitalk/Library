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

public class BookRepositoryImpl implements BookRepository {
    @Override
    public List<Book> findAll() {
        Connection connection = JDBCTools.getConnection();
        String sql ="select * from book,bookcase where book.bookcaseid = bookcase.id";
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<Book> list = new ArrayList<Book>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Book(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getDouble(6),new BookCase(resultSet.getInt(9),resultSet.getString(10))));
                /*
                直接将代码封进add里，减小内存开销
                BookCase bookCase = new BookCase(resultSet.getInt("9"),resultSet.getString("10"));
                Book book =new Book(resultSet.getInt("1"),resultSet.getString("2"),resultSet.getString("3"),resultSet.getString("4"),resultSet.getInt("5"),resultSet.getDouble("6"),bookCase);
                list.add(book);
                 */


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
        }

        return list;
    }
}
