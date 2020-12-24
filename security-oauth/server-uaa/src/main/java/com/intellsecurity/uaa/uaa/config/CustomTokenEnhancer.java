package com.intellsecurity.uaa.uaa.config;

import com.intellsecurity.uaa.uaa.dao.UserDao;
import com.intellsecurity.uaa.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: CustomTokenEnhancer
 * @Description: Token增强器，个性化定制token的内容信息
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/24 9:17
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserDao userDao;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = (User)oAuth2Authentication.getPrincipal();
        //UserDto userDto = (UserDto)oAuth2Authentication.getUserAuthentication().getPrincipal();
        UserDto userDto = userDao.getUserByLogincode(user.getUsername());

        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user_id",userDto.getId());
        additionalInfo.put("user_name",userDto.getUser_name());
        additionalInfo.put("dept_id",userDto.getDept_id());
        additionalInfo.put("dept_name",userDto.getDept_name());
        additionalInfo.put("ent_id",userDto.getEnterprise_id());
        additionalInfo.put("ent_name",userDto.getEnterprise_name());
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
