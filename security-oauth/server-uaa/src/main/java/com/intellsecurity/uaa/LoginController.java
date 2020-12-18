package com.intellsecurity.uaa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.LoginController
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/18 15:05
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login-view")
    public String loginView(){
        return "view/login";
    }

}
