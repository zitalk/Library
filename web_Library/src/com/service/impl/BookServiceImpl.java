package com.service.impl;

import com.entity.Book;
import com.repository.BookRepository;
import com.repository.BorrowRepository;
import com.repository.impl.BookRepositoryImpl;
import com.repository.impl.BorrowRepositoryImpl;
import com.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository =new BookRepositoryImpl();
    private BorrowRepository borrowRepository = new BorrowRepositoryImpl();
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

    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        ///借书时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTime = simpleDateFormat.format(date);
        //还书时间，借书时间+14天
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;
        calendar.set(Calendar.DAY_OF_YEAR, dates);//对日期进行转换
        Date date2 = calendar.getTime();//拿到14天之后的日期
        String returnTime = simpleDateFormat.format(date2);
        borrowRepository.insert(bookid, readerid, borrowTime,returnTime,null,0);//此处体现出包装类Integer的好处，可以接受null
    }
}
