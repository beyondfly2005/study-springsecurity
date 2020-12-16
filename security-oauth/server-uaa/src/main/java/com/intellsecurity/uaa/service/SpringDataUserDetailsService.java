package com.intellsecurity.uaa.service;

import com.intellsecurity.uaa.dao.UserDao;
import com.intellsecurity.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDao.getUserByusername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities("p1").build()  ;
        return userDetails;
    }
}
