package com.service;

import com.entity.Book;
import com.entity.Borrow;

import java.util.List;

public interface BookService {
   public List<Book> findAll(int page);
   public int getPages();
   //Service层拿不到数据，需要从servlet穿过来，后面的参数在读者发送借阅请求时时间可以获取，涉及不到管理员，state默认0
   public void addBorrow(Integer bookid,Integer readerid);
   public List<Borrow> findAllBorrowByReaderId(Integer id);


}
