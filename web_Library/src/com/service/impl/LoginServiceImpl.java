package com.service.impl;

import com.entity.Admin;
import com.entity.Reader;
import com.repository.AdminRepository;
import com.repository.ReaderRepository;
import com.repository.impl.AdminRepositoryImpl;
import com.repository.impl.ReaderRepositoryImpl;
import com.service.LoginService;

/**
 * 接口的实现类，MVC中的M层，负责业务数据。
 */
public class LoginServiceImpl implements LoginService {

    private ReaderRepository readerRepository = new ReaderRepositoryImpl();
    private AdminRepository adminRepository = new AdminRepositoryImpl();

    /**
     * 登录的封装，管理员，读者
     * @param username
     * @param password
     * @param type
     * @return
     */
    @Override
    public Object login(String username, String password,String type) {
        Object object = null;//多态
        switch (type){
            case "reader":
                object = readerRepository.login(username,password);
                break;
            case "admin":
                object = adminRepository.login(username, password);
                break;
        }
        return object;
    }
}
