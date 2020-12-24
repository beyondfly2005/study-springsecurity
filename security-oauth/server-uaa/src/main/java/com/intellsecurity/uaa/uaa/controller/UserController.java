package com.intellsecurity.uaa.uaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.ac.security.oauth2client2.controller.UserController
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/23 11:42
 */
@Controller
public class UserController {

    @RequestMapping(value = "/userinfo")
    @ResponseBody
    public Principal getUserInfo(Principal user){
        return user;
    }

}
