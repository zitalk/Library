package com.service.impl;

import com.entity.Book;
import com.repository.BookRepository;
import com.repository.impl.BookRepositoryImpl;
import com.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository repository =new BookRepositoryImpl();

    @Override
    public List<Book> findAll() {

        return repository.findAll();
    }
}
