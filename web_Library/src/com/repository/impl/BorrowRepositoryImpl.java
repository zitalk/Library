package com.repository.impl;

import com.entity.Book;
import com.entity.Borrow;
import com.entity.Reader;
import com.repository.BorrowRepository;
import com.utils.JDBCTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    /**
     * 插入一条借阅记录，读者用
     * @param bookid
     * @param readerid
     * @param borrowtime
     * @param returntime
     * @param adminid
     * @param state
     */
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "insert into borrow(bookid,readerid,borrowtime,returntime,state) values(?,?,?,?,0)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4,returntime);
            preparedStatement.executeUpdate();//插入之后的更新
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,preparedStatement,null);
        }
    }

    /**
     * 通过读者id查询到所有借阅记录，读者用
     * @param id
     * @param index
     * @param limit
     * @return
     */
    @Override
    public List<Borrow> findAllByReaderId(Integer id,Integer index,Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state from borrow br,book b,reader r where readerid = ? and b.id = br.bookid and r.id = br.readerid limit ?,?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Borrow> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.setInt(2,index);
            statement.setInt(3,limit);
            resultSet = statement.executeQuery();
            //循环
            while(resultSet.next()){
                //取出所有需要的信息,封装进list
//                int borrowId = resultSet.getInt(1);
//                String bookName = resultSet.getString(2);
//                String author = resultSet.getString(3);
//                String publish = resultSet.getString(4);
//                String borrowTime = resultSet.getString(5);
//                String returnTime = ;
//                String readerName = resultSet.getString(7);
//                String tel = resultSet.getString(8);
//                String cardId = resultSet.getString(9);
//                //封装
//                Book book = new Book(bookName,author,publish);
//                Reader reader = new Reader(readerName,tel,cardId);
//                Borrow borrow = new Borrow(borrowId,book,reader,borrowTime,returnTime);
//                list.add(borrow);
                list.add(new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),
                        resultSet.getString(5),
                        resultSet.getString(6),resultSet.getInt(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return list;
    }

    /**
     * 借阅总数，计算出总页数，用于借阅页右下角展示用，读者用
     * @param readerid
     * @return
     */
    @Override
    public int count(Integer readerid) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) from borrow br,book b,reader r where readerid = ? and b.id = br.bookid and r.id = br.readerid";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,readerid);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return count;
    }

    /**
     * 通过state查询到所有借阅记录，管理员用
     * @param state
     * @param index
     * @param limit
     * @return
     */
    @Override
    public List<Borrow> findAllByState(Integer state,Integer index,Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state from borrow br,book b,reader r where state = ? and b.id = br.bookid and r.id = br.readerid limit ?,?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Borrow> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            statement.setInt(2,index);
            statement.setInt(3,limit);
            resultSet = statement.executeQuery();
            //循环
            while(resultSet.next()){
                //取出所有的素材
                list.add(new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),
                        resultSet.getString(5),
                        resultSet.getString(6),resultSet.getInt(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return list;
    }

    /**
     * 通过state查出所有借阅总数，计算出总页数，用于借阅页右下角展示用，管理员用
     * @param state
     * @return
     */
    @Override
    public int countByState(Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "select count(*) from borrow br,book b,reader r where state = ? and b.id = br.bookid and r.id = br.readerid";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return count;
    }

    /**
     * 处理借阅请求，同意，拒绝，归还，管理员用
     * @param borrowId
     * @param state
     * @param adminId
     */
    @Override
    public void handle(Integer borrowId, Integer state, Integer adminId) {
        Connection connection = JDBCTools.getConnection();
        String sql = "update borrow set state = ?,adminid = ? where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            statement.setInt(2,adminId);
            statement.setInt(3,borrowId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,null);
        }
    }
}
