package com.service;

import com.entity.Reader;

/**
 * 写成接口，提高可拓展性
 */
public interface LoginService {
   public Object login(String username,String password,String type);

}
