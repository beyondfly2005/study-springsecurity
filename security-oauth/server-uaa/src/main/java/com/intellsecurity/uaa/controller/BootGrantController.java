package com.intellsecurity.uaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.oauth2.provider.AuthorizationRequest;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.controller.BootGrantController
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/22 16:02
 */
@Controller
@SessionAttributes("authorizationRequest")
public class BootGrantController {

    //@RequestMapping("/oauth/confirm_access")
    @RequestMapping("/custom/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");


        ModelAndView view = new ModelAndView();
        view.setViewName("base-grant");

        view.addObject("clientId", authorizationRequest.getClientId());

        view.addObject("scopes", authorizationRequest.getScope());

        return view;
    }
}
