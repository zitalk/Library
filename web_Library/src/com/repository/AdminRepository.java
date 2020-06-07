package com.repository;

import com.entity.Admin;

/**
 * 管理员登录，对应bookadmin表
 */
public interface AdminRepository {
    public Admin login(String username,String password);
}
