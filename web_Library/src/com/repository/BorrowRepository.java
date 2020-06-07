package com.repository;

import com.entity.Borrow;

import java.util.List;

public interface BorrowRepository {
    //插入一条借阅记录，读者用
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state);

    //通过读者id查询到所有借阅记录，读者用
    public List<Borrow> findAllByReaderId(Integer id, Integer index, Integer limit);

    //借阅总数，计算出总页数，用于借阅页右下角展示用，读者用
    public int count(Integer readerid);

    //通过state查询到所有借阅记录，管理员用
    public List<Borrow> findAllByState(Integer state, Integer index, Integer limit);

    //通过state查出所有借阅总数，计算出总页数，用于借阅页右下角展示用，管理员用
    public int countByState(Integer state);

    //处理借阅请求，同意，拒绝，归还，管理员用
    public void handle(Integer borrowId, Integer state, Integer adminId);
}
