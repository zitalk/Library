package com.service;

import com.entity.Book;
import com.entity.Borrow;

import java.util.List;

public interface BookService {
    //当前页，所有图书的列表，读者用
    public List<Book> findAll(int page);
    //共有多少条图书数据，计算出有多少页数据，在右下角展示，读者用
    public int getPages();
    //当前用户的所有所有借阅的总数，计算出有多少页数据，右下角展示，读者用
    public int getBorrowPages(Integer readerid);
    //Service层拿不到数据，需要从servlet穿过来，后面的参数在读者发送借阅请求时时间可以获取，涉及不到管理员，state默认0
    //借阅，读者用
    public void addBorrow(Integer bookid,Integer readerid);
    //查询所有借阅记录，通过读者的id，读者用
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page);
    //查询所有的借阅记录。通过借阅状态的state，管理员用
    public List<Borrow> findAllBorrowByState(Integer state,Integer page);
    //查询所有借阅记录，计算出有多少页数据，右下角展示，管理员用
    public int getBorrowPagesByState(Integer state);
    //处理借阅，管理员用
    public void handleBorrow(Integer borrowId,Integer state,Integer adminId);
}
