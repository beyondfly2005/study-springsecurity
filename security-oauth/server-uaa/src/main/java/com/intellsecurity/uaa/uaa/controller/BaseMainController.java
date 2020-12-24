package com.intellsecurity.uaa.uaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: BaseMainController
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/22 15:57
 */
@Controller
public class BaseMainController {

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/uaa/auth/authorize");
        return "base-login";
    }
}