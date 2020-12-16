package com.beyondsoft.security.springmvc.service;

import com.beyondsoft.security.springmvc.model.AuthenticationRequest;
import com.beyondsoft.security.springmvc.model.UserDto;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationServiceImpl implements  AuthenticationService{


    @Override
    public UserDto authentication(AuthenticationRequest authenticationRequest) {

        if(authenticationRequest==null || StringUtils.isEmpty(authenticationRequest.getUsername())
        || StringUtils.isEmpty(authenticationRequest.getPassword())){
            throw new RuntimeException("账号或密码为空");
        }

        //根据账号去查询数据库 这里测试程序采用模拟方法
        UserDto userDto = getUserDto(authenticationRequest.getUsername());
        //判断是否为空
        if(userDto==null){
            throw new RuntimeException("查询不到该账户！");
        }
        //校验密码
        if(!authenticationRequest.getPassword().equals(userDto.getPassword())){
            throw new RuntimeException("账号或密码错误");
        }
        //认证通过返回接口的实现信息
        return userDto;
    }

    //根据账号查询用户信息
    private UserDto getUserDto(String username){
        return userMap.get(username);
    }

    //用户信息
    private Map<String ,UserDto> userMap = new HashMap<>();
    {
        userMap.put("zhangsan",new UserDto("1010","zhangsan","123","张三","1313090722"));
        userMap.put("lisi", new UserDto("1011","lisi","456","李四","15369202586"));
    }
}
