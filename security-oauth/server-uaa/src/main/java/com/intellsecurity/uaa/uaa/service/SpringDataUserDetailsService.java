package com.intellsecurity.uaa.uaa.service;

import com.intellsecurity.uaa.uaa.dao.UserDao;
import com.intellsecurity.uaa.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDao.getUserByusername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities("p1").build()  ;
        return userDetails;
    }*/

    @Override
    public UserDetails loadUserByUsername(String logincode) throws UsernameNotFoundException {
        UserDto userDto = userDao.getUserByLogincode(logincode);
        if (userDto == null) {
            throw new UsernameNotFoundException(logincode);
        }
        UserDetails userDetails = User.withUsername(userDto.getLogincode()).password(userDto.getPassword()).authorities("p1").build()  ;
        return userDetails;
    }
}
