package com.intellsecurity.uaa.service;

import com.intellsecurity.uaa.dao.UserDao;
import com.intellsecurity.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.service.UserService
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/25 14:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDto getUserByusername(String username){
        UserDto userDto = null;
        try {
            userDto = userDao.getUserByusername(username);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userDto;
    }

}
