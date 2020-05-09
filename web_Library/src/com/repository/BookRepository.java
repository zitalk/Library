package com.repository;

import com.entity.Book;

import java.util.List;

public interface BookRepository {
    public List<Book> findAll();

}
