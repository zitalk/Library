package com.repository;

import com.entity.Borrow;

import java.util.List;

public interface BorrowRepository {
    public void insert(Integer bookid,Integer readerid,String borrowtime,String returntime,Integer adminid,Integer state);
    public List<Borrow> findAllByReaderId(Integer id);
}
