package com.intellsecurity.uaa.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import com.alibaba.fastjson.JSON;
import com.intellsecurity.uaa.model.UserDto;
import com.intellsecurity.uaa.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intellsecurity.uaa.utils.EncryptUtil;

import javax.servlet.http.HttpServletRequest;

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

<<<<<<< HEAD
    @RequestMapping("/r1")
=======
    @Autowired
    private UserService userService;

    @RequestMapping("r1")
>>>>>>> 1e91871f1247b878cac907700b3b043454ecf79d
    @ResponseBody
//    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(HttpServletRequest request){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = request.getAttribute("json_token").toString();
        if(StringUtils.isNotEmpty(token)) {
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            String principal = JSON.parseObject(json).getString("principal");
            //UserDto userDTO = JSON.parseObject(principal, UserDto.class);

            UserDto userDto = userService.getUserByusername(principal);
            System.out.println(userDto.toString());
        }
        String name="  ";
        return name+"有权限访问r1";
    }
}

