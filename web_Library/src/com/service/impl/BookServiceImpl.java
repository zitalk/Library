package com.service.impl;

import com.entity.Book;
import com.entity.Borrow;
import com.repository.BookRepository;
import com.repository.BorrowRepository;
import com.repository.impl.BookRepositoryImpl;
import com.repository.impl.BorrowRepositoryImpl;
import com.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 有关图书的所有Service，将Repository传过来的数据封装再交给Servlet
 */
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository = new BookRepositoryImpl();
    private BorrowRepository borrowRepository = new BorrowRepositoryImpl();
    private final int LIMIT = 6;//每页展示10行

    /**
     * 当前页，所有图书的列表，读者用
     * @param page
     * @return
     */
    @Override
    public List<Book> findAll(int page) {
        int index = (page-1)*LIMIT;
        return bookRepository.findAll(index,LIMIT);
    }

    /**
     * 共有多少条图书数据，计算出有多少页数据，在右下角展示，读者用
     * @return
     */
    @Override
    public int getPages() {
        int count = bookRepository.count();
        int page = 0;
        if(count % LIMIT == 0){
            page = count/LIMIT;
        }else{
            page = count/LIMIT+1;
        }
        return page;
    }

    /**
     * 当前用户的所有所有借阅的总数，计算出有多少页数据，右下角展示，读者用
     * @param readerid
     * @return
     */
    @Override
    public int getBorrowPages(Integer readerid) {
        int count = borrowRepository.count(readerid);
        int page = 0;
        if(count % LIMIT == 0){
            page = count/LIMIT;
        }else{
            page = count/LIMIT+1;
        }
        return page;
    }

    /**
     * 借阅，读者用
     * @param bookid
     * @param readerid
     */
    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        //借书时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTime = simpleDateFormat.format(date);
        //还书时间，借书时间+14天
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;
        calendar.set(Calendar.DAY_OF_YEAR, dates);//对日期进行转换
        Date date2 = calendar.getTime();//拿到14天之后的日期
        String returnTime = simpleDateFormat.format(date2);
        borrowRepository.insert(bookid,readerid,borrowTime,returnTime,null,0);//此处体现出包装类Integer的好处，可以接受null
    }

    /**
     * 查询所有借阅记录，通过读者的id，读者用
     * @param id
     * @param page
     * @return
     */
    @Override
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page) {
        //业务：将 page 换算成 index,limit
        int index = (page-1)*LIMIT;
        return borrowRepository.findAllByReaderId(id,index,LIMIT);
    }

    /**
     * 查询所有的借阅记录。通过借阅状态的state，管理员用
     * @param state
     * @param page
     * @return
     */
    @Override
    public List<Borrow> findAllBorrowByState(Integer state,Integer page) {
        int index = (page-1)*LIMIT;
        return borrowRepository.findAllByState(state,index,LIMIT);
    }

    /**
     * 查询所有借阅记录，右下角展示，管理员用
     * @param state
     * @return
     */
    @Override
    public int getBorrowPagesByState(Integer state) {
        int count = borrowRepository.countByState(state);
        int page = 0;
        if(count % LIMIT == 0){
            page = count/LIMIT;
        }else{
            page = count/LIMIT+1;
        }
        return page;
    }

    /**
     * 处理借阅，管理员用
     * @param borrowId
     * @param state
     * @param adminId
     */
    @Override
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId) {
        borrowRepository.handle(borrowId,state,adminId);
    }

}
