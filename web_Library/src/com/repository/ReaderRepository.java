package com.repository;

import com.entity.Reader;

/**
 * 读者登录，对应reader表
 */
public interface ReaderRepository {
    public Reader login(String username,String password);
}
