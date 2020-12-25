package com.intellsecurity.uaa.service;

import com.intellsecurity.uaa.model.UserDto;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.service.UserService
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/25 14:07
 */
public interface UserService {

    public UserDto getUserByusername(String username);

}
