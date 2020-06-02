package com.service.impl;

import com.entity.Book;
import com.repository.BookRepository;
import com.repository.impl.BookRepositoryImpl;
import com.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository =new BookRepositoryImpl();
    private final int LIMIT = 6; //每页展示10行
    @Override
    public List<Book> findAll(int page) {

        int index = (page-1)*LIMIT;
        return bookRepository.findAll(index,LIMIT);
    }

    @Override
    public int getPages() {
        int count = bookRepository.count();
        int page = 0;
        if (count % LIMIT==0){
            page = count/LIMIT;
        }else {
            page = count/LIMIT+1;
        }
        return page;
    }
}
