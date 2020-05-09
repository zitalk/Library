package com.repository;

import com.entity.Reader;

public interface ReaderRepository {
    public Reader login(String username,String password);
}
