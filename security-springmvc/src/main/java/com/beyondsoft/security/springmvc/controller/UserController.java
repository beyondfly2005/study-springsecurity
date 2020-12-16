package com.beyondsoft.security.springmvc.controller;

import com.beyondsoft.security.springmvc.model.AuthenticationRequest;
import com.beyondsoft.security.springmvc.model.UserDto;
import com.beyondsoft.security.springmvc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private AuthenticationService authenticationtService;

    @RequestMapping(value = "/login",produces = "text/plain,charset=utf-8")
    public String login(AuthenticationRequest authenticationRequest){
        UserDto userDto = authenticationtService.authentication(authenticationRequest);
        return userDto.getUsername() + "登录成功";
    }
}
