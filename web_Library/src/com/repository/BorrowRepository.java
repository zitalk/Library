package com.repository;

public interface BorrowRepository {
    public void insert(Integer bookid,Integer readerid,String borrowtime,String returntime,Integer adminid,Integer state);
}
