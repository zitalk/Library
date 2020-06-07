package com.repository;

import com.entity.Book;

import java.util.List;

/**
 * 对应book表
 */
public interface BookRepository {
    //到数据库获取书
    public List<Book> findAll(int index, int limit);

    //书的总数
    public int count();
}
