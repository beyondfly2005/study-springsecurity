package com.intellsecurity.uaa.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.controller.OrderController
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/24 13:10
 */
@RestController
public class OrderController {

    @RequestMapping("/r1")
    @ResponseBody
//    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String name="  ";
        return name+"有权限访问r1";
    }
}

